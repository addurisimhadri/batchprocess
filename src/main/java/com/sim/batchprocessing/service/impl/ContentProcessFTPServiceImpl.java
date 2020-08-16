package com.sim.batchprocessing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.batchprocessing.dao.ContentProcessFTPRepository;
import com.sim.batchprocessing.entity.ContentProcessFTP;
import com.sim.batchprocessing.service.ContentProcessFTPService;

@Service
public class ContentProcessFTPServiceImpl implements ContentProcessFTPService {
	
	@Autowired
	ContentProcessFTPRepository contentProcessFTPRepository;

	@Override
	public ContentProcessFTP save(ContentProcessFTP contentProcessFTP) {
		return contentProcessFTPRepository.save(contentProcessFTP);
	}

}
