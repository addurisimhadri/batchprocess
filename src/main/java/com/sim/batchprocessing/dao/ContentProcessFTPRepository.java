package com.sim.batchprocessing.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sim.batchprocessing.entity.ContentProcessFTP;
import com.sim.batchprocessing.entity.ContentProcessFTPId;

@Repository
public interface ContentProcessFTPRepository extends JpaRepository<ContentProcessFTP, ContentProcessFTPId> {
	
	List<ContentProcessFTP> findByContentTypeId(int contentTypeId);
	
	@Query("select cpf  from ContentProcessFTP cpf where processStatus='In Queue' and submitStatus='Completed' order by lastUpdatedTimestamp" )
	Page<ContentProcessFTP> findData(Pageable pageable); 

}
