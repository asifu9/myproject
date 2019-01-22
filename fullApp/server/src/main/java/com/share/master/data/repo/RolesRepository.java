package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.Roles;

public interface RolesRepository extends CrudRepository<Roles, String>{

	
	List<Roles> findByTenantIdAndIsActive(String tenantId,int isActive);
	Roles findById(String id);
	List<Roles> findByIsActive(boolean isActive);
	
}
