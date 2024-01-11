package oop_kiosk_medihub;

import java.util.EventListener;

//
interface DataUpdateListener extends EventListener {
	void onDataUpdate(DocumentItem data);
}

//
class DataUpdateEvent {
	private DocumentItem data;
	
	public DataUpdateEvent(DocumentItem data) {
		this.data = data;
	}
	
	public DocumentItem getData() {
		return data;
	}
}

//
class DataUpdater {
	private DataUpdateListener listener;
	
	public void setListener(DataUpdateListener listener) {
		this.listener = listener;
	}
	public void updateData(DocumentItem data) {
		if (listener != null) {
			listener.onDataUpdate(data);
		}
	}
}
