package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.UserDesignationMap;

public interface UserDesignationMapRepository extends CrudRepository<UserDesignationMap, String>{

	
	List<UserDesignationMap> findByTenantId(String tenantId);
	UserDesignationMap findById(String id);
	List<UserDesignationMap> findByTenantIdAndUserId(String tenantId,String userId);
	List<UserDesignationMap> findByTenantIdAndDesignationId(String tenantId,String designationId);
	
}
