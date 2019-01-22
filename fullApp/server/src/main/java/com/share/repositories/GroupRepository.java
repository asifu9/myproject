package com.share.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.Group;

public interface GroupRepository extends CrudRepository<Group, String>{


	List<Group> findByCreatedByAndTenantId(String createdBy,String TenantId);
	List<Group> findByTenantId(String pkey);
	Group findById(String id);
	Group findByGroupUniqueName(String name);
	
}
