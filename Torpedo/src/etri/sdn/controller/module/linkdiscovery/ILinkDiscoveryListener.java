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

package etri.sdn.controller.module.linkdiscovery;

import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.util.HexString;

import etri.sdn.controller.module.linkdiscovery.ILinkDiscovery.LinkType;
import etri.sdn.controller.protocol.io.IOFSwitch.SwitchType;

/**
 * The user classes of ILinkDiscoveryService should implement this interface.
 * Those user classes notified about the link discovery event via 
 * {@link #linkDiscoveryUpdate(LDUpdate)}. 
 * 
 * This interface has been slightly modified from the original version of Floodlight.
 * 
 * @author bjlee
 *
 */
public interface ILinkDiscoveryListener {
	
	/**
	 * Represents the type of the update.
	 * @author bjlee
	 *
	 */
	public enum UpdateOperation {
		LINK_UPDATED("Link Updated"),
		LINK_REMOVED("Link Removed"),
		SWITCH_UPDATED("Switch Updated"),
		SWITCH_REMOVED("Switch Removed"),
		PORT_UP("Port Up"),
		PORT_DOWN("Port Down");

		private String value;
		UpdateOperation(String v) {
			value = v;
		}

		@Override
		public String toString() {
			return value;
		}
	}
	
	/**
	 * Actual update notification. 
	 * the callback function {@link ILinkDiscoveryListener#linkDiscoveryUpdate(LDUpdate)}
	 * that {@link ILinkDiscoveryService} object calls receives this object as parameter.
	 * 
	 * @author bjlee
	 *
	 */
	public class LDUpdate {
		protected long src;
		protected OFPort srcPort;
		protected long dst;
		protected OFPort dstPort;
		protected SwitchType srcType;
		protected LinkType type;
		protected UpdateOperation operation;

		/**
		 * Constructor
		 * 
		 * @param src		source switch
		 * @param srcPort	source switch port
		 * @param dst		destination switch
		 * @param dstPort	destination switch port
		 * @param type		the type of link ({@link ILinkDiscovery.LinkType})
		 * @param operation	the type of link update operation ({@link UpdateOperation})
		 */
		public LDUpdate(long src, OFPort srcPort,
						long dst, OFPort dstPort,
						LinkType type,
						UpdateOperation operation) {
			this.src = src;
			this.srcPort = srcPort;
			this.dst = dst;
			this.dstPort = dstPort;
			this.type = type;
			this.operation = operation;
		}

		/**
		 * Constructor
		 * 
		 * @param old		LDUpdate object to copy
		 */
		public LDUpdate(LDUpdate old) {
			this.src = old.src;
			this.srcPort = old.srcPort;
			this.dst = old.dst;
			this.dstPort = old.dstPort;
			this.srcType = old.srcType;
			this.type = old.type;
			this.operation = old.operation;
		}

		
		/**
		 * 
		 * Constructor for updatedSwitch(sw)
		 *
		 * @param switchId	id of the switch
		 * @param stype		{@link etri.sdn.controller.protocol.io.IOFSwitch.SwitchType}
		 * @param oper		{@link UpdateOperation}
		 */
		public LDUpdate(long switchId, SwitchType stype, UpdateOperation oper ){
			this.operation = oper;
			this.src = switchId;
			this.srcType = stype;
		}

		/**
		 * Constructor for port up/down
		 * 
		 * @param sw		id of the switch
		 * @param port		port number
		 * @param operation	{@link UpdateOperation}
		 */
		public LDUpdate(long sw, OFPort port, UpdateOperation operation) {
			this.src = sw;
			this.srcPort = port;
			this.operation = operation;
		}

		public long getSrc() {
			return src;
		}

		public OFPort getSrcPort() {
			return srcPort;
		}

		public long getDst() {
			return dst;
		}

		public OFPort getDstPort() {
			return dstPort;
		}

		public SwitchType getSrcType() {
			return srcType;
		}

		public LinkType getType() {
			return type;
		}

		public UpdateOperation getOperation() {
			return operation;
		}

		public void setOperation(UpdateOperation operation) {
			this.operation = operation;
		}

		@Override
		public String toString() {
			switch (operation) {
			case LINK_REMOVED:
			case LINK_UPDATED:
				return "LDUpdate [operation=" + operation +
				", src=" + HexString.toHexString(src)
				+ ", srcPort=" + srcPort
				+ ", dst=" + HexString.toHexString(dst) 
				+ ", dstPort=" + dstPort
				+ ", type=" + type + "]";
			case PORT_DOWN:
			case PORT_UP:
				return "LDUpdate [operation=" + operation +
				", src=" + HexString.toHexString(src)
				+ ", srcPort=" + srcPort + "]";
			case SWITCH_REMOVED:
			case SWITCH_UPDATED:
				return "LDUpdate [operation=" + operation +
				", src=" + HexString.toHexString(src) + "]";
			default:
				return "LDUpdate: Unknown update.";
			}
		}
	}

	/**
	 * Any class that implements ILinkDiscoveryListener receives 
	 * link update notifications via this callback method
	 * 	 
	 * @param update	{@link LDUpdate}
	 */
    public void linkDiscoveryUpdate(LDUpdate update);

}
