package com.sim.batchprocessing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.batchprocessing.dao.SongMetaContentRepository;
import com.sim.batchprocessing.entity.SongMeta;
import com.sim.batchprocessing.service.SongMetaService;

@Service
@Transactional
public class SongMetaServiceImpl implements SongMetaService {
	
	@Autowired
	SongMetaContentRepository songMetaContentRepository;

	@Override
	public SongMeta save(SongMeta songMeta) {
		return songMetaContentRepository.save(songMeta);
	}

	@Override
	public SongMeta findContentCT(int contId, int ctTypeId) {
		return songMetaContentRepository.findBySmIdAndContentTypeId(contId, ctTypeId);
	}

}
