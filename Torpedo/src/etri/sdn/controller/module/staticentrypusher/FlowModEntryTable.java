package etri.sdn.controller.module.staticentrypusher;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openflow.protocol.OFFlowMod;
		
/**
 * This table is of type {@code ConcurrentHashMap<String, Map<String, OFFlowMod>>}.
 * The first key ({@code String}) is DPID, and the second key ({@code String}) is 
 * the name of the flow mod (for example, "flow-mod-1").
 * 
 * @author shkang
 *
 */
public class FlowModEntryTable extends ConcurrentHashMap<String, Map<String, OFFlowMod>> {
		
	private static final long serialVersionUID = -7356949645274537799L;

	public Collection<Map<String, OFFlowMod>> getAllFlowModEntries() {
		return super.values();
	}		

	/**
	 * get flow mod entries for a switch identified by a DPID
	 * 
	 * @param dpid	the DPID of a switch
	 * @return
	 */
	public Map<String, OFFlowMod> getFlowModEntry(String dpid) {
		Map<String, OFFlowMod> flowMod = super.get(dpid);
		return flowMod;
	}
	
}
