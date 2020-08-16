package com.sim.batchprocessing.service;

import java.util.List;

import com.sim.batchprocessing.entity.ContentDevice;

public interface ContentDeviceService {
	
	ContentDevice save(ContentDevice contentDevice);
	List<ContentDevice> getContentDevices(int contId,int ctTypeId);
	ContentDevice getCD(int contId,int ctTypeId,String contFileName);
	void deleteContentDevice(int contId,int ctTypeId);

}
