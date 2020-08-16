package com.sim.batchprocessing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.batchprocessing.dao.ContentRepository;
import com.sim.batchprocessing.entity.Content;
import com.sim.batchprocessing.service.ContentService;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	ContentRepository contentRepository;

	@Override
	public Content save(Content content) {
		return contentRepository.save(content);
	}

	@Override
	public Content findContent(String name, int cpId, int ctTypeId) {
		return contentRepository.findContent(name, cpId, ctTypeId);
	}

	@Override
	public List<Content> getContentByCT(int ctTypeId) {
		return contentRepository.findByCtTypeId(ctTypeId);
	}

	@Override
	public Content findContentCT(int contId, int ctTypeId) {
		return contentRepository.findByContIdAndCtTypeId(contId, ctTypeId);
	}

	@Override
	public List<Content> getApprovedContentByCT(int ctTypeId) {
		return contentRepository.getByCtTypeId(ctTypeId);
	}

}
