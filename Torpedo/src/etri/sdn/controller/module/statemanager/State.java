package etri.sdn.controller.module.statemanager;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.openflow.protocol.OFPort;
import org.openflow.protocol.factory.OFMessageFactory;
import org.openflow.protocol.interfaces.OFFeaturesReply;
import org.openflow.protocol.interfaces.OFMatch;
import org.openflow.protocol.interfaces.OFStatisticsAggregateRequest;
import org.openflow.protocol.interfaces.OFStatisticsDescReply;
import org.openflow.protocol.interfaces.OFStatisticsFlowReply;
import org.openflow.protocol.interfaces.OFStatisticsFlowRequest;
import org.openflow.protocol.interfaces.OFStatisticsPortDescRequest;
import org.openflow.protocol.interfaces.OFStatisticsPortDescReply;
import org.openflow.protocol.interfaces.OFStatisticsPortReply;
import org.openflow.protocol.interfaces.OFStatisticsPortRequest;
import org.openflow.protocol.interfaces.OFStatisticsReply;
import org.openflow.util.HexString;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

import etri.sdn.controller.OFModel;
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.IOFSwitch;

/**
 * Model that represents the internal data of {@link OFMStateManager}. 
 * 
 * @author bjlee
 *
 */
public class State extends OFModel {

	private OFMStateManager manager;
	private long timeInitiated;
	private long totalMemory;
	private OFProtocol protocol;
	/**
	 * Custom Serializer for FEATURES_REPLY message. 
	 * This is used to handle the REST URI /wm/core/switch/{switchid}/features/json.
	 */
	private OFFeaturesReplySerializerModule features_reply_module;
	
	/**
	 * Create the State instance.
	 * 
	 * @param manager	reference to the OFMStateManager module.
	 */
	public State(OFMStateManager manager) {
		this.manager = manager;
		this.timeInitiated = Calendar.getInstance().getTimeInMillis();
		this.totalMemory = Runtime.getRuntime().totalMemory();
		
		this.protocol = (OFProtocol) manager.getController().getProtocol();
		this.features_reply_module = new OFFeaturesReplySerializerModule(this.protocol);
	}
	
	/**
	 * Custom Serializer for OFPort
	 */
	private OFPortSerializerModule port_module = new OFPortSerializerModule();
	
	/**
	 * Custom Serializer for FLOW_STATISTICS_REPLY message.
	 * This is used to handle the REST URI /wm/core/switch/{switchid}/flow/json.
	 */
	private OFFlowStatisticsReplySerializerModule flow_statistics_reply_module 
		= new OFFlowStatisticsReplySerializerModule();
	
	/**
	 * Array of RESTApi objects. 
	 * Each objects represent a REST call handler routine bound to a specific URI.
	 */
	private RESTApi[] apis = {
			
		/**
		 * This object is to implement a REST handler routine for retrieving 
		 * all switch information
		 */
		new RESTApi(
			"/wm/core/controller/switches/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					StringWriter sWriter = new StringWriter();
					JsonFactory f = new JsonFactory();
					JsonGenerator g = null;
					try { 
						g = f.createJsonGenerator(sWriter);
					
						g.writeStartArray();
						for ( IOFSwitch sw : manager.getController().getSwitches() ) {
							g.writeStartObject();
							g.writeFieldName("dpid");
							g.writeString(HexString.toHexString(sw.getId()));
							g.writeFieldName("inetAddress");
							g.writeString(sw.getConnection().getClient().getRemoteAddress().toString());
							g.writeFieldName("connectedSince");
							g.writeNumber(sw.getConnectedSince().getTime());
							g.writeEndObject();
						}
						g.writeEndArray();
						g.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					String r = sWriter.toString();
					response.setEntity(r, MediaType.APPLICATION_JSON);
				}
			}
		),
		
		/**
		 * This object is to implement a REST handler routine 
		 * for retrieving switch aggregate flow statistics
		 */
		new RESTApi(
			"/wm/core/switch/{switchid}/aggregate/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					String switchIdStr = (String) request.getAttributes().get("switchid");
					Long switchId = HexString.toLong(switchIdStr);
					IOFSwitch sw = manager.getController().getSwitch(switchId);
					if ( sw == null ) {
						return;		// switch is not completely set up.
					}
					
					OFStatisticsAggregateRequest req = 
							OFMessageFactory.createStatisticsAggregateRequest(sw.getVersion());
					if ( req.isMatchSupported() ) {
						OFMatch.Builder match = OFMessageFactory.createMatchBuilder(sw.getVersion());
						if ( match.isWildcardsSupported() ) 
							match.setWildcardsWire(0xffffffff);
						req.setMatch(match.build());
					}
					if ( req.isOutPortSupported() ) 
						req.setOutPort(OFPort.NONE);
					if ( req.isOutGroupSupported() ) 
						req.setOutGroup(0xffffffff /* OFPG_ANY (all group) */);
					if ( req.isTableIdSupported() ) 
						req.setTableId((byte)0xff /* OFPTT_ALL (all tables) */);
					req.setLength( req.computeLength() );
	                
					List<OFStatisticsReply> reply = protocol.getSwitchStatistics(sw, req);
					
	                HashMap<String, List<OFStatisticsReply>> output = new HashMap<String, List<OFStatisticsReply>>();
	                if ( reply != null && ! reply.isEmpty() ) {
	                	output.put(switchIdStr, reply);
	                }
	                
	                // create an object mapper.
					ObjectMapper om = new ObjectMapper();
					om.registerModule(port_module);
					
					try {
						String r = om/*.writerWithDefaultPrettyPrinter()*/.writeValueAsString(output);
						response.setEntity(r, MediaType.APPLICATION_JSON);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
			}
		),
		
		/**
		 * This is to implement a REST handler 
		 * for retrieving switch description.
		 */
		new RESTApi(
			"/wm/core/switch/{switchid}/desc/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					String switchIdStr = (String) request.getAttributes().get("switchid");
					Long switchId = HexString.toLong(switchIdStr);
					IOFSwitch sw = manager.getController().getSwitch(switchId);
					if ( sw == null ) {
						return;		// switch is not completely set up.
					}
					
					StringWriter sWriter = new StringWriter();
	                JsonFactory f = new JsonFactory();
	                JsonGenerator g = null;
	                OFStatisticsDescReply desc = protocol.getDescription(sw);
	                
	                try {
	                	g = f.createJsonGenerator(sWriter);
	                	g.writeStartObject();
	                	g.writeFieldName(HexString.toHexString(sw.getId()));
	                	g.writeStartArray();
	                	g.writeStartObject();
	                	g.writeFieldName("datapathDescription");
	                	g.writeString( desc!=null ? desc.getDatapathDescription() : "-" );
	                	g.writeFieldName("hardwareDescription");
	                	g.writeString( desc!=null ? desc.getHardwareDescription() : "-" );
	                	g.writeFieldName("manufacturerDescription");
	                	g.writeString( desc!=null ? desc.getManufacturerDescription() : "-" );
	                	g.writeFieldName("serialNumber");
	                	g.writeString( desc!=null ? desc.getSerialNumber() : "-" );
	                	g.writeFieldName("softwareDescription");
	                	g.writeString( desc!=null ? desc.getSoftwareDescription() : "-" );
	                	g.writeEndObject();
	                	g.writeEndArray();
	                	g.writeEndObject();
	                	g.close();
	                } catch (IOException e) {
	                	e.printStackTrace();
	                }

	                String r = sWriter.toString();
	                response.setEntity(r, MediaType.APPLICATION_JSON);
				}
			}
		),
		
		/**
		 * This object is to implement a REST handler 
		 * for retrieving switch port information (all ports)
		 */
		new RESTApi(
			"/wm/core/switch/{switchid}/port/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {

					String switchIdStr = (String) request.getAttributes().get("switchid");
					Long switchId = HexString.toLong(switchIdStr);
					IOFSwitch sw = manager.getController().getSwitch(switchId);
					if ( sw == null ) {
						return;		// switch is not completely set up.
					}
					
					HashMap<String, List<org.openflow.protocol.interfaces.OFPortStatsEntry>> result = 
						new HashMap<String, List<org.openflow.protocol.interfaces.OFPortStatsEntry>>();
					
					List<org.openflow.protocol.interfaces.OFPortStatsEntry> resultValues;
					result.put( 
						switchIdStr, 
						resultValues = new java.util.LinkedList<org.openflow.protocol.interfaces.OFPortStatsEntry>() 
					);

					OFStatisticsPortRequest req = OFMessageFactory.createStatisticsPortRequest(sw.getVersion());
					req.setPort(OFPort.NONE);

					List<OFStatisticsReply> reply = protocol.getSwitchStatistics(sw, req);

					for ( OFStatisticsReply s : reply ) {
						if ( s instanceof OFStatisticsPortReply ) {
							resultValues.addAll( ((OFStatisticsPortReply)s).getEntries() );
						}
					}

					// create an object mapper.
					ObjectMapper om = new ObjectMapper();
					// this is critical in providing the port statistics correctly.
					om.registerModule(port_module);
					
					try {
						String r = om./*writerWithDefaultPrettyPrinter().*/writeValueAsString(result);
						response.setEntity(r, MediaType.APPLICATION_JSON);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
			}
		),
		
		/**
		 * This object is to implement a REST handler 
		 * to retrieve switch feature (FEATURES_REPLY) 
		 */
		new RESTApi(
			"/wm/core/switch/{switchid}/features/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					
					String switchIdStr = (String) request.getAttributes().get("switchid");
					Long switchId = HexString.toLong(switchIdStr);
					IOFSwitch sw = manager.getController().getSwitch(switchId);
					if ( sw == null ) {
						return;		// switch is not completely set up.
					}
					
					OFStatisticsPortDescRequest pdreq = OFMessageFactory.createStatisticsPortDescRequest(sw.getVersion());
					if ( pdreq == null ) {
						// this switch version is lower than 1.3. It does not support OFStatisticsPortDescRequest
						OFFeaturesReply reply = protocol.getFeaturesReply(sw);
						
						HashMap<String, OFFeaturesReply> result = new HashMap<String, OFFeaturesReply>();
						result.put( switchIdStr, reply );
						
						// create an object mapper.
						ObjectMapper om = new ObjectMapper();
						om.registerModule(features_reply_module);
						
						try {
							String r = om./*writerWithDefaultPrettyPrinter().*/writeValueAsString(result);
							response.setEntity(r, MediaType.APPLICATION_JSON);
						} catch (Exception e) {
							e.printStackTrace();
							return;
						}
					} else {
						// the switch supports version 1.3
						List<OFStatisticsReply> reply = protocol.getSwitchStatistics( sw, pdreq );
						
						if ( reply != null && ! reply.isEmpty() ) {
							HashMap<String, OFStatisticsPortDescReply> result = new HashMap<String, OFStatisticsPortDescReply>();
							result.put( switchIdStr, (OFStatisticsPortDescReply) reply.remove(0) );
							
							// create an object mapper.
							ObjectMapper om = new ObjectMapper();
							om.registerModule(features_reply_module);
							
							try {
								String r = om./*writerWithDefaultPrettyPrinter().*/writeValueAsString(result);
								response.setEntity(r, MediaType.APPLICATION_JSON);
							} catch (Exception e) {
								e.printStackTrace();
								return;
							}
						}
					}
				}
			}
		),
		
		/**
		 * This object is to implement a REST handler 
		 * to retrieve FLOW_STATISTICS_REPLY message content
		 */
		new RESTApi(
			"/wm/core/switch/{switchid}/flow/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					
					String switchIdStr = (String) request.getAttributes().get("switchid");
					Long switchId = HexString.toLong(switchIdStr);
					IOFSwitch sw = manager.getController().getSwitch(switchId);
					if ( sw == null ) {
						return;		// switch is not completely set up.
					}
					
					HashMap<String, List<org.openflow.protocol.interfaces.OFFlowStatsEntry>> result = 
						new HashMap<String, List<org.openflow.protocol.interfaces.OFFlowStatsEntry>>();
					List<org.openflow.protocol.interfaces.OFFlowStatsEntry> resultValues = 
						new java.util.LinkedList<org.openflow.protocol.interfaces.OFFlowStatsEntry>();
					result.put(switchIdStr, resultValues);
										
					OFStatisticsFlowRequest req = OFMessageFactory.createStatisticsFlowRequest(sw.getVersion());
					if ( req.isMatchSupported() ) {
						OFMatch.Builder match = OFMessageFactory.createMatchBuilder(sw.getVersion());
						if ( match.isWildcardsSupported() )
							match.setWildcardsWire(0xffffffff);
						req.setMatch(match.build());
					}
					if ( req.isOutPortSupported() ) 
						req.setOutPort(OFPort.NONE);
					if ( req.isOutGroupSupported() ) 
						req.setOutGroup(0xffffffff /* OFPG_ANY (all group) */);
					if ( req.isTableIdSupported() )
						req.setTableId((byte)0xff /* OFPTT_ALL (all tables) */);

					List<OFStatisticsReply> reply = protocol.getSwitchStatistics(sw, req);
					for ( OFStatisticsReply s : reply ) {
						if ( s instanceof OFStatisticsFlowReply ) {
							resultValues.addAll( ((OFStatisticsFlowReply)s).getEntries() );
						}
					}
					
					// create an object mapper.
					ObjectMapper om = new ObjectMapper();
					om.registerModule(flow_statistics_reply_module);
					om.registerModule(port_module);
					
					try {
						String r = om/*.writerWithDefaultPrettyPrinter()*/.writeValueAsString(result);
						response.setEntity(r, MediaType.APPLICATION_JSON);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
			}
		),
		
		/**
		 * This object is to implement a REST handler 
		 * to retrieve controller system health-related information 
		 */
		new RESTApi(
			"/wm/core/health/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					
					StringWriter sWriter = new StringWriter();
					JsonFactory f = new JsonFactory();
					JsonGenerator g = null;
					try {
						g = f.createJsonGenerator(sWriter);
						g.writeStartObject();
						g.writeFieldName("host");
						g.writeString("localhost");
						g.writeFieldName("ofport");
						g.writeNumber(6633);
						g.writeFieldName("uptime");
						Interval temp = new Interval(timeInitiated, Calendar.getInstance().getTimeInMillis());
						Period tempPeriod = temp.toPeriod();
						g.writeString(
							String.format(
								"System is up for %d days %d hours %d minutes %d seconds",
								tempPeriod.getDays(),
								tempPeriod.getHours(),
								tempPeriod.getMinutes(),
								tempPeriod.getSeconds()
							)
						);
						g.writeFieldName("free");
						g.writeString(Runtime.getRuntime().freeMemory()/1024/1024 + "M");
						g.writeFieldName("total");
						g.writeString(totalMemory/1024/1024 + "M");
						g.writeFieldName("healthy");
						g.writeBoolean(true);
						g.writeFieldName("modules");
						g.writeStartArray();
						String[] moduleNames = manager.getController().getModuleNames();
						if ( moduleNames != null ) {
							for ( String s : moduleNames ) {
								g.writeString(s);
							}
						}
						g.writeEndArray();
						g.writeFieldName("moduleText");
						g.writeString(manager.getController().getConcatenatedModuleNames());
						g.writeEndObject();
						g.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

					String r = sWriter.toString();
					
					response.setEntity(r, MediaType.APPLICATION_JSON);
				}
			}
		),
		
		/**
		 * This object is to implement a REST handler 
		 * for retrieving module information (list of modules)
		 */
		new RESTApi(
			"/wm/core/module/{type}/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					String typeStr = (String) request.getAttributes().get("type");
					if ( typeStr.equals("loaded") ) {
											
						// create an object mapper.
						ObjectMapper om = new ObjectMapper();
						om.registerModule( new ModuleListSerializerModule());
						
						try {
							String r = om.writerWithDefaultPrettyPrinter().writeValueAsString( manager.getController() );
							response.setEntity(r, MediaType.APPLICATION_JSON);
						} catch (Exception e) {
							e.printStackTrace();
							return;
						}
					}
				}
			}
		),
		
		/**
		 * This object is to implement a REST handler 
		 * that exports memory status. 
		 */
		new RESTApi(
			"/wm/core/memory/json",
			new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					StringWriter sWriter = new StringWriter();
					JsonFactory f = new JsonFactory();
					JsonGenerator g = null;
					try {
						g = f.createJsonGenerator(sWriter);
						g.writeStartObject();
						g.writeFieldName("total");
						g.writeString(totalMemory/1024/1024 + "M");
						g.writeFieldName("free");
						g.writeString(Runtime.getRuntime().freeMemory()/1024/1024 + "M");
						g.writeEndObject();
						g.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

					String r = sWriter.toString();
					
					response.setEntity(r, MediaType.APPLICATION_JSON);
				}
			}
		)
	};
	
	/**
	 * Returns the list of all RESTApi objects
	 * 
	 * @return		array of all RESTApi objects
	 */
	@Override
	public RESTApi[] getAllRestApi() {
		return this.apis;
	}
}
