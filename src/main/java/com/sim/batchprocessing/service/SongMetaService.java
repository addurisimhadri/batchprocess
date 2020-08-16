package com.sim.batchprocessing.service;

import com.sim.batchprocessing.entity.SongMeta;

public interface SongMetaService {
	
	public SongMeta save(SongMeta songMeta);
	SongMeta findContentCT(int contId,int ctTypeId);

}
