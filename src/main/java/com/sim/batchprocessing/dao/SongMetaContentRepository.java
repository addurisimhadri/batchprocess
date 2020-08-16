package com.sim.batchprocessing.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.batchprocessing.entity.SongContentId;
import com.sim.batchprocessing.entity.SongMeta;

public interface SongMetaContentRepository extends JpaRepository<SongMeta, SongContentId> {
	
	SongMeta findBySmIdAndContentTypeId(int smId, int contentTypeId);
	
}
