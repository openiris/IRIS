package etri.sdn.controller.module.storagemanager;

public interface StorageListenable {
	
		public void addStorageListener(IStorageListener listener, StorageEvent event);
		public void removeStorageListener(IStorageListener listener);
		void notifyListeners(StorageEvent storageEvent);
}
