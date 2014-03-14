package etri.sdn.controller.module.statemanager;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.projectfloodlight.openflow.protocol.OFAggregateStatsRequest;
import org.projectfloodlight.openflow.protocol.OFDescStatsReply;
import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFeaturesReply;
import org.projectfloodlight.openflow.protocol.OFFlowStatsEntry;
import org.projectfloodlight.openflow.protocol.OFFlowStatsReply;
import org.projectfloodlight.openflow.protocol.OFFlowStatsRequest;
import org.projectfloodlight.openflow.protocol.OFPortDescStatsReply;
import org.projectfloodlight.openflow.protocol.OFPortDescStatsRequest;
import org.projectfloodlight.openflow.protocol.OFPortStatsEntry;
import org.projectfloodlight.openflow.protocol.OFPortStatsReply;
import org.projectfloodlight.openflow.protocol.OFPortStatsRequest;
import org.projectfloodlight.openflow.protocol.OFStatsReply;
import org.projectfloodlight.openflow.protocol.OFStatsRequestFlags;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.types.OFGroup;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.util.HexString;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

import etri.sdn.controller.OFModel;
import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.protocol.rest.serializer.ModuleListSerializerModule;
import etri.sdn.controller.protocol.rest.serializer.OFFeaturesReplySerializerModule;
import etri.sdn.controller.protocol.rest.serializer.OFFlowStatisticsReplySerializerModule;
import etri.sdn.controller.protocol.rest.serializer.OFTypeSerializerModule;

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
	 * Custom Serializer for OF types
	 */
	private OFTypeSerializerModule type_module = new OFTypeSerializerModule();
	
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
						OFFactory fac = OFFactories.getFactory(sw.getVersion());

						OFAggregateStatsRequest.Builder req = fac.buildAggregateStatsRequest();
						Match match = fac.matchWildcardAll();
						
						req.setMatch(match);

						req.setOutPort(OFPort.ANY /* NONE for 1.0 */);
						try { 
							// this should be fixed to accept OFGroup object in the further release of Loxigen.
							req.setOutGroup(OFGroup.ANY);
							req.setTableId(TableId.ALL);
						} catch ( UnsupportedOperationException u ) { 
							// does nothing.
						}

						List<OFStatsReply> reply = protocol.getSwitchStatistics(sw, req.build());

						HashMap<String, List<OFStatsReply>> output = new HashMap<String, List<OFStatsReply>>();
						if ( reply != null && ! reply.isEmpty() ) {
							output.put(switchIdStr, reply);
						}

						// create an object mapper.
						ObjectMapper om = new ObjectMapper();
						om.registerModule(type_module);

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
						OFDescStatsReply desc = protocol.getSwitchInformation(sw).getDescStatsReply();

						try {
							g = f.createJsonGenerator(sWriter);
							g.writeStartObject();
							g.writeFieldName(HexString.toHexString(sw.getId()));
							g.writeStartArray();
							g.writeStartObject();
							g.writeFieldName("datapathDescription");
							g.writeString( desc!=null ? desc.getDpDesc() : "-" );
							g.writeFieldName("hardwareDescription");
							g.writeString( desc!=null ? desc.getHwDesc() : "-" );
							g.writeFieldName("manufacturerDescription");
							g.writeString( desc!=null ? desc.getMfrDesc() : "-" );
							g.writeFieldName("serialNumber");
							g.writeString( desc!=null ? desc.getSerialNum() : "-" );
							g.writeFieldName("softwareDescription");
							g.writeString( desc!=null ? desc.getSwDesc() : "-" );
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

						HashMap<String, List<OFPortStatsEntry>> result = 
								new HashMap<String, List<OFPortStatsEntry>>();

						List<OFPortStatsEntry> resultValues;
						result.put( 
								switchIdStr, 
								resultValues = new java.util.LinkedList<OFPortStatsEntry>() 
								);

						OFPortStatsRequest.Builder req = OFFactories.getFactory(sw.getVersion()).buildPortStatsRequest();
						req.setPortNo(OFPort.ANY /* NONE for 1.0 */);

						List<OFStatsReply> reply = protocol.getSwitchStatistics(sw, req.build());

						for ( OFStatsReply s : reply ) {
							if ( s instanceof OFPortStatsReply ) {
								resultValues.addAll( ((OFPortStatsReply)s).getEntries() );
							}
						}
						
						StringWriter sWriter = new StringWriter();
						JsonFactory f = new JsonFactory();
						
						try {
							JsonGenerator g = f.createJsonGenerator(sWriter);
							g.writeStartObject();
							g.writeFieldName(switchIdStr);
							g.writeStartArray();
							for ( OFPortStatsEntry entry : resultValues ) {

								g.writeStartObject();
								
								try { 
									g.writeNumberField("portNumber", entry.getPortNo().getPortNumber());
									g.writeNumberField("transmitBytes", entry.getTxBytes().getValue());
									g.writeNumberField("receiveBytes", entry.getRxBytes().getValue());
									g.writeNumberField("transmitPackets", entry.getTxPackets().getValue());
									g.writeNumberField("receivePackets", entry.getRxPackets().getValue());
									g.writeNumberField("transmitDropped", entry.getTxDropped().getValue());
									g.writeNumberField("receiveDropped", entry.getRxDropped().getValue());
									g.writeNumberField("transmitErrors", entry.getTxErrors().getValue());
									g.writeNumberField("receiveErrors", entry.getRxErrors().getValue());
									g.writeNumberField("receiveFrameErrors", entry.getRxFrameErr().getValue());
									g.writeNumberField("receiveOverErrors", entry.getRxOverErr().getValue());
									g.writeNumberField("receiveCrcErros", entry.getRxCrcErr().getValue());
									g.writeNumberField("collisions", entry.getCollisions().getValue());
									g.writeNumberField("durationSec", entry.getDurationSec());
									g.writeNumberField("durationNSec", entry.getDurationNsec());
								} catch ( UnsupportedOperationException u ) {
									// does nothing.
								}
								g.writeEndObject();
							}
							
							g.writeEndArray();
							g.writeEndObject();
							g.close();
						} catch (IOException e) {
							e.printStackTrace();
							return;
						}
						
						String r = sWriter.toString();
						response.setEntity(r, MediaType.APPLICATION_JSON);
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
					
					try { 
						OFPortDescStatsRequest pdreq = OFFactories.getFactory(sw.getVersion()).portDescStatsRequest(EnumSet.noneOf(OFStatsRequestFlags.class));
						
						// the switch supports version 1.3
						List<OFStatsReply> reply = protocol.getSwitchStatistics( sw, pdreq );
						
						if ( reply != null && ! reply.isEmpty() ) {
							HashMap<String, OFPortDescStatsReply> result = new HashMap<String, OFPortDescStatsReply>();
							result.put( switchIdStr, (OFPortDescStatsReply) reply.remove(0) );
							
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
						
					} catch ( UnsupportedOperationException u ) {
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
					
					OFFactory fac = OFFactories.getFactory(sw.getVersion());
					
					HashMap<String, List<OFFlowStatsEntry>> result = 
						new HashMap<String, List<OFFlowStatsEntry>>();
					List<OFFlowStatsEntry> resultValues = 
						new java.util.LinkedList<OFFlowStatsEntry>();
					result.put(switchIdStr, resultValues);
										
					OFFlowStatsRequest.Builder req = fac.buildFlowStatsRequest();
					req
					.setMatch( fac.matchWildcardAll() )
					.setOutPort( OFPort.ANY /* NONE for 1.0*/ );
					try {
						req
						.setOutGroup(OFGroup.ANY /* OFPG_ANY (all group) */)
						.setTableId(TableId.ALL);
					} catch ( UnsupportedOperationException u ) {}

					try { 
						List<OFStatsReply> reply = protocol.getSwitchStatistics(sw, req.build());
						for ( OFStatsReply s : reply ) {
							if ( s instanceof OFFlowStatsReply ) {
								resultValues.addAll( ((OFFlowStatsReply)s).getEntries() );
							}
						}
					} catch ( Exception e ) {
						e.printStackTrace();
						return;
					}
					
					// create an object mapper.
					ObjectMapper om = new ObjectMapper();
					om.registerModule(flow_statistics_reply_module);
					om.registerModule(type_module);
					
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
