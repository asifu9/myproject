package com.share.repositories;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.Wall;

public interface WallRepository extends CrudRepository<Wall, String>{

	
	Wall findByTenantIdAndId(String pkey,String id);
	Wall findById(String id);
	
}
