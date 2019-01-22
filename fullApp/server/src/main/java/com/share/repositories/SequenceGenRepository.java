package com.share.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.SequenceGen;





public interface SequenceGenRepository extends CrudRepository<SequenceGen, String>{

	
	List<SequenceGen> findByTenantId(String tenantId);
	SequenceGen findByUniqueId(String id);
	
}
