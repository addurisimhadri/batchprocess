package com.sim.batchprocessing.service;

import java.util.List;
import java.util.Optional;

import com.sim.batchprocessing.entity.ContentType;

public interface ContentTypeService {

	public Optional<ContentType> getContentType(int contentId);
	public List<ContentType> getCTs();
	public ContentType save(ContentType contentType);
	public List<ContentType> getCTs(long ctTypeId,String active);
	public ContentType getCType(int contentId);
}
