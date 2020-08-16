package com.sim.batchprocessing.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sim.batchprocessing.entity.PhysicalFolder;

@Repository
public interface PhysicalFolderRepository extends JpaRepository<PhysicalFolder, Integer> {
	
	Optional<PhysicalFolder> findByFolderName(String folderName);
	List<PhysicalFolder> findByContentTypeId(int contentTypeId);

}
