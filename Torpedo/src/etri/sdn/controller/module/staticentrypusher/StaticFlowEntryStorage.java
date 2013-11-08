/**
 *    Copyright 2011, Big Switch Networks, Inc. 
 *    Originally created by David Erickson, Stanford University
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License"); you may
 *    not use this file except in compliance with the License. You may obtain
 *    a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 **/

package etri.sdn.controller.module.staticentrypusher;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
//import org.openflow.protocol.factory.BasicFactory;
import org.openflow.protocol.ver1_0.messages.OFFlowMod;
import org.openflow.protocol.ver1_0.messages.OFMatch;
import org.openflow.protocol.ver1_0.types.OFMessageType;
import org.openflow.util.U16;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import etri.sdn.controller.OFModel;
import etri.sdn.controller.protocol.version.VersionAdaptor10;
import etri.sdn.controller.util.Logger;

public class StaticFlowEntryStorage extends OFModel  {

	public OFMStaticFlowEntryPusher manager;

	private String name;
	
	/** 
	 * see {@link FlowEntryTable}
	 */
	private FlowEntryTable flowEntryTable;
	
	/**
	 * see {@link FlowModEntryTable}
	 */
	private FlowModEntryTable flowModEntryTable;
	
	/**
	 * This is a reverse index from a flow-mod name to the DPID.
	 */
	private Map<String, String> entryToDpidMap;

//    private BasicFactory ofMessageFactory;

	private VersionAdaptor10 version_adaptor_10;

	StaticFlowEntryStorage(OFMStaticFlowEntryPusher manager, String name) {
		this.manager = manager;
		this.name = name;
		flowEntryTable = new FlowEntryTable();
		this.version_adaptor_10 = (VersionAdaptor10) manager.getController().getVersionAdaptor((byte)0x01);
	}
	
	public OFMStaticFlowEntryPusher getManager() {
		return this.manager;
	}

	public void setTableName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public FlowEntryTable getFlowEntryTable() {
		return flowEntryTable;
	}
		
	public FlowModEntryTable getFlowModEntryTable() {
		return flowModEntryTable;
	}
	
	public String getDpidByEntryName (String name) {
		return entryToDpidMap.get(name);
	}
	
	public Map<String, String> getEntryToDpidMap() {
		return entryToDpidMap;
	}
	
    /**
     * Take a single row, turn it into a flowMod, and add it to the entries.
     * {$dpid}.{$entryName}=FlowMod 
     * 
     * IF an entry is in active, mark it with FlowMod = null
     * 
     * @param row		a single flow-mod record to be converted
     * @param entries	object where the conversion result is stored
     */              
    public void parseStaticFlowEntry(Map<String, Object> row, Map<String, Map<String, OFFlowMod>> entries) 
    {
        String switchName = null;
        String entryName = null;

        StringBuffer matchString = new StringBuffer();
//        if (ofMessageFactory == null) // lazy init
//        	ofMessageFactory = new BasicFactory();
//
//        OFFlowMod flowMod = (OFFlowMod) ofMessageFactory.getMessage(OFType.FLOW_MOD);
        OFFlowMod flowMod = (OFFlowMod) OFMessageType.FLOW_MOD.newInstance();

        if (!row.containsKey(StaticFlowEntryType.COLUMN_SWITCH) || !row.containsKey(StaticFlowEntryType.COLUMN_NAME)) {
            Logger.debug("skipping entry with missing required 'switch' or 'name' entry");
            return;
        }
        // most error checking done with ClassCastException
        try {
            // first, snag the required entries, for debugging info
            switchName = (String) row.get(StaticFlowEntryType.COLUMN_SWITCH);
            entryName = (String) row.get(StaticFlowEntryType.COLUMN_NAME);
            if (!entries.containsKey(switchName))
                entries.put(switchName, new HashMap<String, OFFlowMod>());
            StaticFlowEntryType.initDefaultFlowMod(flowMod, entryName);
            
            for (String key : row.keySet()) {
                if (row.get(key) == null)
                    continue;
                if ( key.equals(StaticFlowEntryType.COLUMN_SWITCH) || key.equals(StaticFlowEntryType.COLUMN_NAME) || key.equals("id"))
                    continue; // already handled
                // explicitly ignore and wildcards
                if (key.equals(StaticFlowEntryType.COLUMN_WILDCARD) || key.equals(StaticFlowEntryType.COLUMN_HARD_TIMEOUT)
                		|| key.equals(StaticFlowEntryType.COLUMN_IDLE_TIMEOUT))
                	continue;
//                if ( key.equals(StaticFlowEntryType.COLUMN_HARD_TIMEOUT)) {
//                	flowMod.setHardTimeout(U16.t(Integer.valueOf((String)row.get(StaticFlowEntryType.COLUMN_HARD_TIMEOUT))));
//                	continue;
//                } else if ( key.equals(StaticFlowEntryType.COLUMN_IDLE_TIMEOUT)) {
//                	flowMod.setIdleTimeout(U16.t(Integer.valueOf((String)row.get(StaticFlowEntryType.COLUMN_IDLE_TIMEOUT))));
//                	continue;
//                }             	
                
                if ( key.equals(StaticFlowEntryType.COLUMN_ACTIVE)) {
                    if  (! Boolean.valueOf((String) row.get(StaticFlowEntryType.COLUMN_ACTIVE))) {
                        Logger.debug("skipping inactive entry {} for switch {}", entryName, switchName);
                        entries.get(switchName).put(entryName, null);  // mark this an inactive
                        return;
                    }
                } else if ( key.equals(StaticFlowEntryType.COLUMN_ACTIONS)){
                	StaticFlowEntryType.parseActionString(flowMod, (String) row.get(StaticFlowEntryType.COLUMN_ACTIONS));
                } else if ( key.equals(StaticFlowEntryType.COLUMN_COOKIE)) {
                    flowMod.setCookie(
                    		StaticFlowEntryType. computeEntryCookie(flowMod, Integer.valueOf((String) row.get(StaticFlowEntryType.COLUMN_COOKIE)), entryName)
                        );
                } else if ( key.equals(StaticFlowEntryType.COLUMN_PRIORITY)) {
                    flowMod.setPriority(U16.t(Integer.valueOf((String) row.get(StaticFlowEntryType.COLUMN_PRIORITY))));
                } else { // the rest of the keys are for OFMatch().fromString()
                	if (matchString.length() > 0)
                        matchString.append(",");
                    matchString.append(key + "=" + row.get(key).toString());
                }
            }
        } catch (ClassCastException e) {
            if (entryName != null && switchName != null)
                Logger.debug(
                        "skipping entry {} on switch {} with bad data : "
                                + e.getMessage(), entryName, switchName);
            else
                Logger.debug("skipping entry with bad data: {} :: {} ",
                        e.getMessage(), e.getStackTrace());
        }

//        OFMatch ofMatch = new OFMatch();
//        String match = matchString.toString();
//        
//        try {
//            ofMatch.fromString(match);
//        } catch (IllegalArgumentException e) {
//            Logger.debug(
//                    "ignoring flow entry {} on switch {} with illegal OFMatch() key: "
//                            + match, entryName, switchName);
//            return;
//        }
        OFMatch ofMatch = version_adaptor_10.loadOFMatchFromString(matchString.toString());
        
        flowMod.setMatch(ofMatch);

        entries.get(switchName).put(entryName, flowMod);
    }

    /**
     * used for debugging and unit tests
     * @return the number of static flow entries as cached from storage
     */
    public int countEntries() {
        int size = 0;
        if (flowModEntryTable == null)
            return 0;
        for (String ofswitch : flowModEntryTable.keySet())
            size += flowModEntryTable.get(ofswitch).size();
        return size;
    }

    /**
     * Create mappings from each name of the flow-mod record to the DPID of a switch.
     * The mapping result (return value) will be stored in {@link #entryToDpidMap}. 
     * This method is called by {@link #startUp()}. 
     * 
     * @param flowEntry		flow mod entry table
     * @return				mappings from the flow mode name to the DPID
     */
    protected Map<String, String> computeEntry2DpidMap(FlowModEntryTable flowEntry) {
        Map<String, String> ret = new HashMap<String, String>();
        for(String dpid : flowEntry.keySet()) {
            for( String entry: flowEntry.get(dpid).keySet())
                ret.put(entry, dpid);
        }
        return ret;
    }
    
    /**
     * Read static flowMod entries from storageSource, and store them in a hash.
     * This method is called by {@link #startUp()}.
     * 
     * @return		{@link FlowModEntryTable} object
     */
    private FlowModEntryTable buildFlowModEntryTable() {
        FlowModEntryTable flowModEntryTable = new FlowModEntryTable();
        try {
            Map<String, Object> row;
            for (Iterator<Map<String, Object>> it = flowEntryTable.getAllFlowEntries().iterator(); it.hasNext();) {
                row = it.next();
                parseStaticFlowEntry(row, flowModEntryTable);
            }
        } catch (Exception e) {
            Logger.error("failed to access storage: {}", e.getMessage());
            // if the table doesn't exist, then wait to populate later via
            // setStorageSource()
        }
        return flowModEntryTable;
    }

	
    /**
     * Start-up this model. It first creates a flow-mod entry table ({@link FlowModEntryTable}),
     * and create mappings from the flow-mod record name to the DPIDs. 
     */
    public void startUp() {     
    	flowModEntryTable = buildFlowModEntryTable();
    	entryToDpidMap = computeEntry2DpidMap(flowModEntryTable);  
    }

	private RESTApi[] apis = {
			new RESTApi("/wm/staticflowentrypusher/list/{switch}/json",
					new Restlet() {
						@Override
						public void handle(Request request, Response response) {
							StringWriter sWriter = new StringWriter();
							JsonFactory f = new JsonFactory();
							JsonGenerator g = null;
							
							String switchId = (String) request.getAttributes().get("switch");
							FlowModEntryTable table = getFlowModEntryTable();
							if ( table == null ) {
								return;
							}
							
							Set<String> flows;
							if (switchId.toLowerCase().equals("all")) {
								flows = getFlowEntryTable().keySet();
							} else {
								Map<String, OFFlowMod> fmod = getFlowModEntryTable().get(switchId);
								if (fmod != null) {
									flows = fmod.keySet();
								} else {
									flows = null;
								}
							}
							
							//Map<String, OFFlowMod> flows = table.get(switchId);
																					
							try {
								g = f.createJsonGenerator(sWriter);
								g.writeStartObject();
								if (flows != null) {
									Iterator<String> i = flows.iterator();
									while (i.hasNext()) {
										String flow = i.next();
										g.writeFieldName(flow);
										g.writeString(getFlowEntryTable().getFlowEntry(flow).toString());
									}
								} else {
									g.writeFieldName("result");
									g.writeString("There is no static flows for the given switch");
								}
								g.writeEndObject();
								g.close();
					
							} catch (Exception e) {
								e.printStackTrace();
								return;
							}
							String r = sWriter.toString();
							response.setEntity(r, MediaType.APPLICATION_JSON);
						}
					}),
			
			new RESTApi("/wm/staticflowentrypusher/clear/{switch}/json",
					new Restlet() {
						@Override
						public void handle(Request request, Response response) {
							StringWriter sWriter = new StringWriter();
							JsonFactory f = new JsonFactory();
							JsonGenerator g = null;
							
							String switchId = (String) request.getAttributes().get("switch");
							Set<String> flows;
							if (switchId.toLowerCase().equals("all")) {
								flows = new HashSet<String>( getFlowEntryTable().keySet() );
							} else {
								Map<String, OFFlowMod> fmod = getFlowModEntryTable().get(switchId);
								if (fmod != null) {
									flows = new HashSet<String>(fmod.keySet() );
								} else {
									flows = null;
								}
								
							}
							//System.out.println(flows.toString());
							try {
								Iterator<String> i = flows.iterator();
								while (i.hasNext()) {
									getManager().deleteFlow((String)i.next());
								}
								g = f.createJsonGenerator(sWriter);
								g.writeStartObject();
								g.writeFieldName("result");
								g.writeString("clear-flows for the given switch completed");
								g.writeEndObject();
								g.close();
							} catch (Exception e) {
								e.printStackTrace();
								return;
							}
							String r = sWriter.toString();
							response.setEntity(r, MediaType.APPLICATION_JSON);
						}
					}),
					new RESTApi("/wm/staticflowentrypusher/json",
							new Restlet() {
								@Override
								public void handle(Request request, Response response) {
									StringWriter sWriter = new StringWriter();
									JsonFactory f = new JsonFactory();
									JsonGenerator g = null;
									
									Method m = request.getMethod();
									String entityText = request.getEntityAsText();
									entityText = entityText.replaceAll("[\']", "");
									//System.out.println(entityText);
									if (m==Method.POST) {
										try {
											Map<String, Object> storageEntry = StaticFlowEntryType.jsonToStorageEntry(entityText);
											
											Object flowName = storageEntry.get(new String("name"));
											g = f.createJsonGenerator(sWriter);
											g.writeStartObject();
											if (flowName != null) {
												String status = null;
												if (!StaticFlowEntryType.checkMatchIp(storageEntry)) {
										                status = "Warning! Pushing a static flow entry that matches IP " +
										                        "fields without matching for IP payload (ether-type 2048) will cause " +
										                        "the switch to wildcard higher level fields.";
										                Logger.error(status);
										        } else {
										                status = "Entry pushed";
										        }
												getFlowEntryTable().insertFlowEntry((String)flowName, storageEntry);
												HashSet<String> s = new HashSet<String>();
												s.add((String) flowName);
												manager.entriesModified(s);																								
												//System.out.println("flowName: " + flowName);
												//System.out.println("flows: " + manager.getFlows());
												g.writeFieldName("result");
												g.writeString(flowName + ": " + status);
											} else {
												g.writeFieldName("result");
												g.writeString("invalid flow entry (no name field)");
											}
											g.writeEndObject();
											g.close();
											
										} catch (Exception e){
											e.printStackTrace();
											return;
										}
										
									} else if (m==Method.DELETE) {
										try {
											Map<String, Object> storageEntry = StaticFlowEntryType.jsonToStorageEntry(entityText);
											Object flowName = storageEntry.get(new String("name"));
											boolean r = getManager().deleteFlow((String)flowName);
											g = f.createJsonGenerator(sWriter);
											g.writeStartObject();
											g.writeFieldName("result");
											if (r) {
												g.writeString("clear-flow:" + flowName + " is completed");
											} else {
												g.writeString("clear-flow:" + flowName + " does not exist");
											}
											g.writeEndObject();
											g.close();
											
										} catch (Exception e){
											e.printStackTrace();
											return;
										}
										
									}
												
									String r = sWriter.toString();
									response.setEntity(r, MediaType.APPLICATION_JSON);
								}								
							})
			};

	@Override
	public RESTApi[] getAllRestApi() {
		return apis;
	}
}
