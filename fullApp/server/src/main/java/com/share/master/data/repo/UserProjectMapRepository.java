package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.UserProjectMap;

public interface UserProjectMapRepository extends CrudRepository<UserProjectMap, String>{

	
	List<UserProjectMap> findByTenantId(String tenantId);
	List<UserProjectMap>  findByTenantIdAndProjectId(String tenantId,String projectId);
	List<UserProjectMap> findByTenantIdAndUserId(String tenantId,String userId);
	UserProjectMap findById(String id);
	
}
