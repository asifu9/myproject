package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.ConfigActivities;


public interface ConfigActivitiesRepository extends CrudRepository<ConfigActivities, String>{

	
	List<ConfigActivities> findByTenantIdAndIsActive(String tenantId,boolean isActive);
	ConfigActivities findByTenantIdAndName(String tenantId,String name);
	ConfigActivities findById(String id);
	
}
