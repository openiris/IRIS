package etri.sdn.controller.module.ml2;

import etri.sdn.controller.IService;
import etri.sdn.controller.module.ml2.RestNetwork.NetworkDefinition;
import etri.sdn.controller.module.ml2.RestPort.PortDefinition;
import etri.sdn.controller.module.ml2.RestSubnet.SubnetDefinition;

/**
 * This class currently is just a placeholder.
 * There's no identified services now.
 *
 * @author bjlee
 *
 */
public interface IOpenstackML2ConnectorService extends IService {

	/**
	 * Return list of all virtual networks.
	 * @param netId The ID exists single network info. The ID not exists all network info.
	 * @param netKey 
	 * @param netValue 
	 * @return String network info jsonStr
	 */
	public String listNetworks(String netId, String netKey, String netValue);

	/**
	 * Creates a new virtual network. This can also be called
	 * to modify a virtual network. To update a network you specify the GUID
	 * and the fields you want to update.
	 * @param network : NetworkDefinition - network info
	 */
	public void createNetwork(NetworkDefinition network);

	/**
	 * Deletes a virtual network.
	 * @param netId The ID (not name) of virtual network to delete.
	 */
	public void deleteNetwork(String netId);

	/**
	 * Return list of all virtual subnet.
	 * @param subId The ID exists single subnet info. The ID not exists all subnet info.
	 * @param subKey
	 * @param subValue
	 * @return String subnet info jsonStr
	 */
	public String listSubnets(String subId, String subKey, String subValue);

	/**
	 * Creates a new virtual subnet. This can also be called
	 * to modify a virtual subnet. To update a subnet you specify the GUID
	 * and the fields you want to update.
	 * @param subnet : SubnetDefinition - subnet info
	 */
	public void createSubnet(SubnetDefinition subnet);

	/**
	 * Deletes a virtual subnet.
	 * @param subId The ID (not name) of virtual subnet to delete.
	 */
	public void deleteSubnet(String subId);

	/**
	 * Return list of all virtual port.
	 * @param porId The ID exists single port info. The ID not exists all port info.
	 * @parm porKey
	 * @param porValue
	 * @return String port info jsonStr
	 */
	public String listPorts(String porId, String porKey, String porValue);

	/**
	 * Creates a new virtual port. This can also be called
	 * to modify a virtual port. To update a port you specify the GUID
	 * and the fields you want to update.
	 * @param port : PortDefinition - port info
	 */
	public void createPort(PortDefinition port);

	/**
	 * Deletes a virtual port.
	 * @param porId The ID (not name) of virtual port to delete.
	 */
	public void deletePort(String porId);
}