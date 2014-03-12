/**
 *    Copyright 2011,2012, Big Switch Networks, Inc. 
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

package etri.sdn.controller.module.devicemanager;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import org.projectfloodlight.openflow.protocol.OFMessage;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.module.devicemanager.IDeviceService.DeviceField;
import etri.sdn.controller.protocol.io.Connection;

/**
 * This is a default entity classifier that simply classifies all
 * entities into a fixed entity class, with key fields of MAC and VLAN.
 * 
 * @author readams
 */
public final class OFMDefaultEntityClassifier extends OFModule implements IEntityClassifierService
{

	private static EnumSet<DeviceField> keyFields;

	static {
		keyFields = EnumSet.of(DeviceField.MAC, DeviceField.VLAN);
	}

	/**
	 * A default fixed entity class
	 */
	private static class DefaultEntityClass implements IEntityClass {
		String name;

		public DefaultEntityClass(String name) {
			this.name = name;
		}

		@Override
		public EnumSet<IDeviceService.DeviceField> getKeyFields() {
			return keyFields;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	private static DefaultEntityClass entityClass = new DefaultEntityClass("DefaultEntityClass");
	
	/*
	 * IEntityClassifierService methods
	 */

	/**
	 * This choose a proper IEntityClass object to which the entity belongs.
	 * 
	 * @param entity entity to classify
	 * 
	 * @return an IEntityClass object
	 */
	@Override
	public IEntityClass classifyEntity(Entity entity) {
		return entityClass;
	}

	@Override
	public IEntityClass reclassifyEntity(IDevice curDevice,
			Entity entity) {
		return entityClass;
	}

	@Override
	public void deviceUpdate(IDevice oldDevice, 
			Collection<? extends IDevice> newDevices) {
		// no-op
	}

	/**
	 * Returns the primary key-fields to classify entities.
	 */
	@Override
	public EnumSet<DeviceField> getKeyFields() {
		return keyFields;
	}
	
	@Override
	public void addListener(IEntityClassListener listener) {
		// no-op
	}
	
	/*
	 * OFModule methods
	 */

	@Override
	protected void initialize() {
		registerModule(IEntityClassifierService.class, this);
	}

	@Override
	protected boolean handleHandshakedEvent(Connection conn,
			MessageContext context) {
		return true;
	}

	@Override
	protected boolean handleMessage(Connection conn, MessageContext context,
			OFMessage msg, List<OFMessage> outgoing) {
		return true;
	}
	
	@Override
	protected boolean handleDisconnect(Connection conn) {
		return true;
	}

	@Override
	public OFModel[] getModels() {
		// TODO Auto-generated method stub
		return null;
	}

}
