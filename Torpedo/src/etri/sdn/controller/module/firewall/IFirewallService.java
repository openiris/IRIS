package etri.sdn.controller.module.firewall;

import java.util.List;
import java.util.Map;

import etri.sdn.controller.IService;

/**
 * The interface to define basic operations of the firewall module.
 * 
 */
public interface IFirewallService extends IService {

    /**
     * Enables/disables the firewall.
     * 
     * @param enable true when enabled, false when disabled
     */
    public void enableFirewall(boolean enable);

    /**
     * Returns operational status of the firewall.
     * 
     * @return true when enabled, false when disabled
     */
    public boolean isEnabled();
 
    /**
     * Returns all firewall rules.
     * 
     * @return a list of all firewall rules
     */
    public List<FirewallRule> getRules();
    
    /**
     * Returns the subnet mask.
     * 
     * @return the subnet mask
     */
    public String getSubnetMask();
    
    /**
     * Sets the subnet mask
     * 
     * @param newMask The new subnet mask
     */
    public void setSubnetMask(String newMask);

    /**
     * Returns all firewall rules of the persistent database.
     * 
     * @return a list of all firewall rules
     */
    public List<Map<String, Object>> getStorageRules();

    /**
     * Adds a new firewall rule into the memory storage and the persistent database.
     * 
     * @param rule a new firewall rule
     */
    public void addRule(FirewallRule rule);

    /**
     * Deletes the firewall rule using the ruleid from the memory storage 
     * and the persistent database.
     * 
     * @param ruleid the ruleid of firewall rule to delete
     */
    public void deleteRule(int ruleid);
    
    /**
     * Delete all the rules from the memory storage and the persistent database.
     */
    public void clearRules();
}
