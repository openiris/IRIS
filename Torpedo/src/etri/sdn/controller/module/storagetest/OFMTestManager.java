package etri.sdn.controller.module.storagetest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openflow.protocol.OFMessage;

import etri.sdn.controller.MessageContext;
import etri.sdn.controller.OFModel;
import etri.sdn.controller.OFModule;
import etri.sdn.controller.TorpedoProperties;
import etri.sdn.controller.module.storagemanager.IStorageListener;
import etri.sdn.controller.module.storagemanager.IStorageService;
import etri.sdn.controller.module.storagemanager.OFMStorageManager;
import etri.sdn.controller.module.storagemanager.StorageEvent;
import etri.sdn.controller.protocol.io.Connection;

public class OFMTestManager extends OFModule implements IStorageListener{

	private OFMStorageManager storageInstance;
	private TestStorage testStorage;
	String dbName = null;
	String collectionName = null;
	@Override
	protected void initialize() {
		TorpedoProperties conf = TorpedoProperties.loadConfiguration();
		this.storageInstance = (OFMStorageManager) getModule(IStorageService.class);
		
		this.testStorage = new TestStorage(storageInstance);
		this.dbName = conf.getString("storage-default-db");
		this.collectionName = testStorage.getName();
		
		Map<String, Integer> key = new HashMap<String, Integer>();
		key.put("key test", StorageEvent.STORAGE_OPERATION_INSERT);
		key.put("key name", StorageEvent.STORAGE_OPERATION_DELETE);
		StorageEvent storageEventInstance = new StorageEvent("mydb","test", key);
		
		this.storageInstance.addStorageListener(this, storageEventInstance);
	}

	@Override
	protected boolean handleHandshakedEvent(Connection conn,
			MessageContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean handleMessage(Connection conn, MessageContext context,
			OFMessage msg, List<OFMessage> outgoing) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean handleDisconnect(Connection conn) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OFModel[] getModels() {
		return new OFModel[] { this.testStorage };
	}

	@Override
	public void storageUpdate(StorageEvent event) {
		System.out.println(":: storage operation " + event.getValue() + " performed.");
	}

}
