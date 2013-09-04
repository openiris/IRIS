package etri.sdn.controller.module.staticentrypusher;


import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * This table is of type {@code TreeMap<String, Map<String, Object>>}.
 * 
 * The first key is the name of the flow entry name (for example, "flow-mod-1"). 
 * The second key is the name of the attribute that constitutes a flow-mod record
 * (for example, "set-vlan-id").
 * 
 * @author bjlee
 *
 */
public class FlowEntryTable extends TreeMap<String, Map<String, Object>>{

	private static final long serialVersionUID = -610938691778823739L;

	/**
	 * retrieve a flow entry from the table by its name
	 * 
	 * @param entryName		name of the flow entry to retrieve
	 * @return				a flow entry {@code Map<String, Object>})
	 */
	public Map<String, Object> getFlowEntry(String entryName) {
		Map<String, Object> entry = super.get(entryName);
		return entry;
	}
	
	/**
	 * insert a new flow entry in the table. 
	 * 
	 * @param flowName		name of the flow entry ({@code String})
	 * @param flowEntry		mappings between keys ({@code String}) and values ({@code Object})
	 */
	public void insertFlowEntry(String flowName, Map<String, Object> flowEntry) {
		assert (flowName != null);
		super.put(flowName, flowEntry);
	}

	/**
	 * Delete the existing flow entry in the table by its name. 
	 * 
	 * @param flowName		name of the flow entry to delete ({@code String})
	 */
	public void deleteFlowEntry(String flowName) {
		super.remove(flowName);
	}

	/**
	 * Returns all the flow entries within the table. 
	 * 
	 * @return	{@code Collection<Map<String, Object>>} object
	 */
	public Collection<Map<String, Object>> getAllFlowEntries() {
		return super.values();
	}
}
