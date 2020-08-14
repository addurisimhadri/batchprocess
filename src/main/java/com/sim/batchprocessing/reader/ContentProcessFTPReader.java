package com.sim.batchprocessing.reader;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import com.sim.batchprocessing.entity.ContentProcessFTP;

@Component
public class ContentProcessFTPReader implements ItemReader<ContentProcessFTP> {
	private static final Logger logger = LoggerFactory.getLogger(ContentProcessFTPReader.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	
	
	int index;
	
	@PostConstruct
	public void init() {
		
	}
	
	@Override
	public ContentProcessFTP read()
			throws Exception {
		ContentProcessFTP entity = null;
		logger.info(myMarker," ==============={} " ,entity);
		return entity;
	}

}
