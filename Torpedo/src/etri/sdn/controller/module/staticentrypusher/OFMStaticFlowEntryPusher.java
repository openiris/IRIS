package etri.sdn.controller.module.staticentrypusher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.openflow.protocol.OFMessage;
import org.openflow.protocol.ver1_0.messages.OFFlowMod;
import org.openflow.protocol.ver1_0.types.OFFlowModCommand;
import org.openflow.util.HexString;
import org.openflow.util.U16;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.protocol.io.Connection;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.util.Logger;

/**
 * <p>
 * This module is responsible for maintaining a set of static flows on switches.
 * This is just a big dumb list of flows and something external is
 * responsible for ensuring they make sense for the network.
 * </p>
 * <p>
 * This module implements the {@link IStaticFlowEntryPusherService}. 
 * </p>
 * 
 * @author shkang
 * 
 */
public class OFMStaticFlowEntryPusher extends OFModule implements
		IStaticFlowEntryPusherService {

	protected StaticFlowEntryStorage staticFlowEntryStorage;

	/**
	 * This method does initialization as follows:
	 * <ol>
	 * <li> register this module as an implementation of the {@link IStaticFlowEntryPusherService}. 
	 * <li> create a {@link StaticFlowEntryStorage} object and starts it
	 * </ol>
	 */
	@Override
	protected void initialize() {
		registerModule(IStaticFlowEntryPusherService.class, this);
		this.staticFlowEntryStorage = new StaticFlowEntryStorage(this, "StaticFlowEntryStorage");
		staticFlowEntryStorage.startUp();
	}

	/**
	 * This method only returns true, which means no module-specific operations are required. 
	 */
	@Override
	protected boolean handleHandshakedEvent(Connection conn,
			MessageContext context) {
		return true;
	}

	/**
	 * This method only returns true, which means no module-specific operations are required. 
	 */
	@Override
	protected boolean handleMessage(Connection conn, MessageContext context,
			OFMessage msg, List<OFMessage> outgoing) {
		return true;
	}

	@Override
	public OFModel[] getModels() {
		// TODO Auto-generated method stub
		// new return OFModel[] { this.table };
		return new OFModel[] { this.staticFlowEntryStorage };
	}

	/**
	 * Class to sort FlowMod's by priority, from lowest to highest
	 * 
	 * @author shkang
	 */
	class FlowModSorter implements Comparator<String> {
		//private String dpid;
		Map<String, OFFlowMod> flowModEntries;

		public FlowModSorter(Map<String, OFFlowMod> flowModEntries) {
			//this.dpid = dpid;
			this.flowModEntries = flowModEntries;
		}

		@Override
		public int compare(String o1, String o2) {
			OFFlowMod f1 = flowModEntries.get(o1);
			OFFlowMod f2 = flowModEntries.get(o2);
			if (f1 == null || f2 == null) // sort active=false flows by key
				return o1.compareTo(o2);
			return U16.f(f1.getPriority()) - U16.f(f2.getPriority());
		}
	};

	/**
	 * <p>
	 * Reads from our entriesFromStorage for the specified switch and sends the
	 * FlowMods down to the controller in <b>sorted</b> order.
	 * </p>
	 * <p>
	 * Sorted is important to maintain correctness of the switch: if a packet
	 * would match both a lower and a higher priority rule, then we want it to
	 * match the higher priority or nothing, but never just the lower priority
	 * one. Inserting from high to low priority fixes this.
	 * </p>
	 * <p>
	 * TODO consider adding a "block all" flow mod and then removing it while
	 * starting up.
	 * </p>
	 * 
	 * @param sw	The switch to send entries to
	 */
	protected void sendEntriesToSwitch(IOFSwitch sw) {
		String dpid = sw.getStringId();
		if (staticFlowEntryStorage != null) {
			Map<String, OFFlowMod> flowModEntries = staticFlowEntryStorage.getFlowModEntryTable().getFlowModEntry(dpid);
			List<String> sortedList = new ArrayList<String>(flowModEntries.keySet());
			// weird that Collections.sort() returns void
			Collections.sort(sortedList, new FlowModSorter(flowModEntries));
			for (String entryName : sortedList) {
				OFFlowMod flowMod = flowModEntries.get(entryName);
				if (flowMod != null) {
					Logger.debug("Pushing static entry {} for {}", dpid,
							entryName);
					writeFlowModToSwitch(sw, flowMod);
				}
			}
		}
	}

	/**
	 * <p>
	 * If this method is called, it sends entries to switch by calling
	 * {@link #sendEntriesToSwitch(IOFSwitch)}. 
	 * </p>
	 * <p>
	 * WARNING: currently, this method is called by nowhere.
	 * </p>
	 * 
	 * @param sw
	 */
	public void addedSwitch(IOFSwitch sw) {
		Logger.debug("addedSwitch {}; processing its static entries", sw);
		sendEntriesToSwitch(sw);
	}

	/**
	 * This does nothing currently, except for emitting some log messages.
	 * 
	 * @param sw
	 */
	public void removedSwitch(IOFSwitch sw) {
		Logger.debug("removedSwitch {}", sw);
		// do NOT delete from our internal state; we're tracking the rules,
		// not the switches
	}

	/**
	 * This method currently does nothing.
	 * 
	 * @param switchId
	 */
	public void switchPortChanged(Long switchId) {
		// no-op
	}

	/**
	 * This handles both rowInsert() and rowUpdate()
	 */
	public void entriesModified(Set<String> entryNames) {

		HashMap<String, Map<String, OFFlowMod>> entriesToAdd = new HashMap<String, Map<String, OFFlowMod>>();
		// build up list of what was added
		for (String entryName : entryNames) {
			Map<String, Object> flowEntry = staticFlowEntryStorage.getFlowEntryTable().getFlowEntry(entryName);
			staticFlowEntryStorage.parseStaticFlowEntry(flowEntry, entriesToAdd);
		}
		// batch updates by switch and blast them out
		for (String dpid : entriesToAdd.keySet()) {
			if (!staticFlowEntryStorage.getFlowModEntryTable().containsKey(dpid))
				staticFlowEntryStorage.getFlowModEntryTable().put(dpid, new HashMap<String, OFFlowMod>());
			List<OFMessage> outQueue = new ArrayList<OFMessage>();
			for (String entry : entriesToAdd.get(dpid).keySet()) {
				OFFlowMod newFlowMod = entriesToAdd.get(dpid).get(entry);
				OFFlowMod oldFlowMod = staticFlowEntryStorage.getFlowModEntryTable().get(dpid).get(entry);
				if (oldFlowMod != null) { // remove any pre-existing rule
					oldFlowMod.setCommand(OFFlowModCommand.DELETE_STRICT);
					outQueue.add(oldFlowMod);
				}
				if (newFlowMod != null) {
					staticFlowEntryStorage.getFlowModEntryTable().get(dpid).put(entry, newFlowMod);
					outQueue.add(newFlowMod);
					staticFlowEntryStorage.getEntryToDpidMap().put(entry, dpid);
				} else {
					staticFlowEntryStorage.getFlowModEntryTable().get(dpid).remove(entry);
					staticFlowEntryStorage.getEntryToDpidMap().remove(entry);
				}
			}
			writeOFMessagesToSwitch(HexString.toLong(dpid), outQueue);
		}
	}

	/**
	 * Writes a list of OFMessages to a switch.
	 * 
	 * @param dpid		The datapath ID of the switch to write to
	 * @param messages	The list of OFMessages to write.
	 */
	private void writeOFMessagesToSwitch(long dpid, List<OFMessage> messages) {
		IOFSwitch ofswitch = this.controller.getSwitch(dpid);
		if (ofswitch != null) { // is the switch connected
			try {
				Logger.debug("Sending {} new entries to {}", messages.size(),
						dpid);
				ofswitch.getConnection().write(messages);
				ofswitch.getConnection().flush();
			} catch (Exception e) {
				Logger.error("Tried to write to switch {} but got {}", dpid,
						e.getMessage());
			}
		}
	}

	/**
	 * delete a specific set of flow mod records 
	 * 
	 * @param names		names of the flod-mod records 
	 */
	public void deleteFlows(Set<String> names) {
		Logger.debug("deleting entries");
		for (String name : names) {
			deleteFlow(name);
		}
	}

	@Override
	public void deleteAllFlows() {
		for (String entry : staticFlowEntryStorage.getEntryToDpidMap().keySet()) {
			deleteFlow(entry);
		}
	}
	  
	/**
	 * delete flow by the given flowName
	 * 
	 * @param flowName	the name of the flow mod record.
	 */
	public boolean deleteFlow(String flowName) {
		String dpid = staticFlowEntryStorage.getEntryToDpidMap().get(flowName);
		Logger.debug("Deleting flow {} for switch {}", flowName, dpid);
		if (dpid == null) {
			Logger.error("inconsistent internal state: no switch has rule {}",
					flowName);
			return false;
		}
		
		//delete entry from FlowEntryTable
		staticFlowEntryStorage.getFlowEntryTable().deleteFlowEntry(flowName);
		
		//delete entry from EntryToDpidMap
		staticFlowEntryStorage.getEntryToDpidMap().remove(flowName);
		
		
		OFFlowMod flowMod = staticFlowEntryStorage.getFlowModEntryTable().get(dpid).get(flowName);
		flowMod.setCommand(OFFlowModCommand.DELETE_STRICT);

		if (staticFlowEntryStorage.getFlowModEntryTable().containsKey(dpid)
				&& staticFlowEntryStorage.getFlowModEntryTable().get(dpid).containsKey(flowName)) {
			
			//delete flowMod from FlowModEntryTable
			staticFlowEntryStorage.getFlowModEntryTable().get(dpid).remove(flowName);
		} else {
			Logger.debug("Tried to delete non-existent entry {} for switch {}",
					flowName, dpid);
			return false;
		}

		// send delete_flow_mod to switch
		writeFlowModToSwitch(HexString.toLong(dpid), flowMod);
		return true;
	}

	/**
	 * Writes an OFFlowMod to a switch. It checks to make sure the switch exists
	 * before it sends.
	 * 
	 * @param dpid		The data to write the flow mod to
	 * @param flowMod	The OFFlowMod to write
	 */
	private void writeFlowModToSwitch(long dpid, OFFlowMod flowMod) {
		IOFSwitch ofSwitch = this.controller.getSwitch(dpid);
		if (ofSwitch == null) {
			Logger.debug("Not deleting key {} :: switch {} not connected", flowMod.toString(), dpid);
			return;
		}
		writeFlowModToSwitch(ofSwitch, flowMod);
	}

	/**
	 * Writes an OFFlowMod to a switch.
	 * 
	 * @param sw		The IOFSwitch to write to
	 * @param flowMod	The OFFlowMod to write
	 */
	private void writeFlowModToSwitch(IOFSwitch sw, OFFlowMod flowMod) {
		// try {
		sw.getConnection().write(flowMod);
		sw.getConnection().flush();
		// } catch (IOException e) {
		// log.error("Tried to write OFFlowMod to {} but failed: {}",
		// HexString.toHexString(sw.getId()), e.getMessage());
		// }
	}

	/**
	 * add a flow mod record. The name of the records is also stored within the given map object.
	 * 
	 * @param entry		flow mod record with map representation ('name' is included).
	 * @return			true if correctly added, or false.
	 */
	public boolean addFlowEntry (Map<String, Object> entry) {
		//get flow name
		String name = (String) entry.get(new String("name"));
		if (name !=null) {
			//insert flow entry to FlowEntryTable
			staticFlowEntryStorage.getFlowEntryTable().insertFlowEntry(name, entry);
			return true;
		} else {
			Logger.debug("Invalid Static Flow Entry: No name field");
			return false;
		}
	}

	@Override
	public void addFlow(String name, OFFlowMod fm, String swDpid) {
		Map<String, Object> fmMap = StaticFlowEntryType.flowModToStorageEntry(fm, swDpid, name);
		
		//flowEntryTable update
		staticFlowEntryStorage.getFlowEntryTable().insertFlowEntry(name, fmMap);
		
		//entryToDpidMap update
		staticFlowEntryStorage.getEntryToDpidMap().put(name, swDpid);
		
		//flowModEntryTable update
		Map<String, OFFlowMod> switchEntries = staticFlowEntryStorage.getFlowModEntryTable().get(swDpid);
		if (switchEntries == null) {
			switchEntries = new HashMap<String, OFFlowMod>();
			staticFlowEntryStorage.getFlowModEntryTable().put(swDpid, switchEntries);
		}
		switchEntries.put(name, fm);
		
		//send add_flow_mod to switch
		writeFlowModToSwitch(HexString.toLong(swDpid), fm);
	}


	@Override
	public void deleteFlowsForSwitch(long dpid) {
		String sDpid = HexString.toHexString(dpid);

		for (Entry<String, String> e : staticFlowEntryStorage.getEntryToDpidMap().entrySet()) {
			if (e.getValue().equals(sDpid))
				deleteFlow(e.getKey());
		}
	}

	@Override
	public Map<String, Map<String, OFFlowMod>> getFlows() {
		return staticFlowEntryStorage.getFlowModEntryTable();
	}

	@Override
	public Map<String, OFFlowMod> getFlows(String dpid) {
		return staticFlowEntryStorage.getFlowModEntryTable().get(dpid);
	}

	@Override
	protected boolean handleDisconnect(Connection conn) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Return the {@link StaticFlowEntryStorage} object. 
	 * 
	 * @return		{@link StaticFlowEntryStorage} object.
	 */
	public StaticFlowEntryStorage getStaticFlowEntryStorage() {
		return staticFlowEntryStorage;
	}
}
