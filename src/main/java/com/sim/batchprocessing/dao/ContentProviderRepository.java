package com.sim.batchprocessing.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.batchprocessing.entity.ContentProvider;

public interface ContentProviderRepository extends JpaRepository<ContentProvider, Integer> {

	ContentProvider getByCpName(String cpName);	

}
