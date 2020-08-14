package com.sim.batchprocessing.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sim.batchprocessing.entity.ContentProcessFTP;

@Component
public class ContentProcessFTPProcessor implements ItemProcessor<ContentProcessFTP, ContentProcessFTP> {
	private static final Logger logger = LoggerFactory.getLogger(ContentProcessFTPProcessor.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");	
	
	@Value("${upload.unziplocation}")
	public  String unZipLocation;	
	
	
	@Override
	public ContentProcessFTP process(ContentProcessFTP item) throws Exception {			 
		logger.info(myMarker, " ContentProcessFTP  {} ",item);
		return item;
	}
	
	

}
