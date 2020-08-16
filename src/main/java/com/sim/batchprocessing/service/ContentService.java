package com.sim.batchprocessing.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.sim.batchprocessing.entity.Content;

public interface ContentService {
	
	public Content save(Content content);
	public Content findContent(@Param("name") String name, @Param("cpId") int cpId, @Param("ctTypeId") int ctTypeId);
	public List<Content> getContentByCT(int ctTypeId);
	Content findContentCT(int contId,int ctTypeId);
	public List<Content> getApprovedContentByCT(int ctTypeId);

}
