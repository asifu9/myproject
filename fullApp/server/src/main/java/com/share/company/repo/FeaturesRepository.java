package com.share.company.repo;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.Features;
import com.share.domain.core.FormAttachment;

public interface FeaturesRepository extends CrudRepository<Features, String>{

	
	
	Features findById(String id);
	
	Features findByName(String name);
	
}
