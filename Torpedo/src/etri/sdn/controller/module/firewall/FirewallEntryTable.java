package etri.sdn.controller.module.firewall;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is a storage in the memory to manage firewall rules.
 * This storage uses a firewall rule id as a key.
 *  
 */
public class FirewallEntryTable extends TreeMap<String, Map<String, Object>>{

	private static final long serialVersionUID = 3500875463251362189L;

	/**
	 * Returns the firewall rule associated with the given ruleid.
	 * 
	 * @param ruleid the ruleid of firewall rule to be searched
	 * 
	 * @return Map-type firewall rule searched by ruleid
	 * 
	 */
	public Map<String, Object> getFirewallEntry(String ruleid) {
		Map<String, Object> entry = super.get(ruleid);
		return entry;
	}
	
	/**
	 * Stores a firewall rule into the memory storage.
	 * 
	 * @param ruleid the ruleid of firewall rule to insert
	 * @param entry the firewall rule to insert
	 * 
	 */
	public void insertFirewallEntry(String ruleid, Map<String, Object> entry) {
		assert (ruleid != null);
		super.put(ruleid, entry);
	}

	/**
	 * Removes the firewall rule associated with the given ruleid.
	 * 
	 * @param ruleid the ruleid of firewall rule to delete
	 * 
	 */
	public void deleteFirewallEntry(String ruleid) {
		super.remove(ruleid);
	}

	/**
	 * Returns all firewall rules of the memory storage.
	 * 
	 * @return all firewall rules of the memory storage
	 * 
	 */
	public Collection<Map<String, Object>> getAllFirewallEntries() {
		return super.values();
	}
	
}
