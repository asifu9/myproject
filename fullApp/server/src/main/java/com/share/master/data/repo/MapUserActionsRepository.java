package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.MapUserActions;

public interface MapUserActionsRepository extends CrudRepository<MapUserActions, String>{

	
	List<MapUserActions> findByTenantIdAndUserId(String tenantId,String userId);
	List<MapUserActions> findByTenantIdAndUserIdAndType(String tenantId,String userId,String type);
	List<MapUserActions> findByTenantIdAndUserIdAndTypeAndIsActive(String tenantId,String userId,String type,boolean isActive);
	List<MapUserActions> findByTenantIdAndType(String tenantId,String type);
	MapUserActions findById(String id);
	List<MapUserActions> findByTenantIdAndTypeValueAndType(String tenantId, String typedValue, String type);
	
}
