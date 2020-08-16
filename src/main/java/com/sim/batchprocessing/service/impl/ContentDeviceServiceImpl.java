package com.sim.batchprocessing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.batchprocessing.dao.ContentDeviceRepository;
import com.sim.batchprocessing.entity.ContentDevice;
import com.sim.batchprocessing.service.ContentDeviceService;
@Service
@Transactional
public class ContentDeviceServiceImpl implements ContentDeviceService {

	@Autowired
	ContentDeviceRepository contentDeviceRepository;
	@Override
	public ContentDevice save(ContentDevice contentDevice) {
		return contentDeviceRepository.save(contentDevice);
	}

	@Override
	public List<ContentDevice> getContentDevices(int contId, int ctTypeId) {
		return contentDeviceRepository.getContentDevice(contId, ctTypeId);
	}

	@Override
	public ContentDevice getCD(int contId, int ctTypeId, String contFileName) {
		return contentDeviceRepository.getCD(contId, ctTypeId, contFileName);
	}

	@Override
	public void deleteContentDevice(int contId, int ctTypeId) {
		contentDeviceRepository.deleteContentDevice(contId, ctTypeId);
	}

}
