package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.UserOrgDesgMap;

public interface UserOrgDesgMapRepository extends CrudRepository<UserOrgDesgMap, String>{

	
	List<UserOrgDesgMap> findByTenantId(String tenantId);
	List<UserOrgDesgMap> findByTenantIdAndUserId(String tenantId,String userId);
	UserOrgDesgMap findById(String id);
	
}
