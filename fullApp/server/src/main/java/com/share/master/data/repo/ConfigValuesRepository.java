package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.ConfigValues;

public interface ConfigValuesRepository extends CrudRepository<ConfigValues, String>{

	
	List<ConfigValues> findByTenantIdAndTypeAndIsActiveAndLocale(String TenantId,String type,boolean isActive,String locale);
	ConfigValues findById(String id);
	ConfigValues findByTenantIdAndTypeAndId(String TenantId,String type,String  id);
	List<ConfigValues> findByTenantIdAndIsActive(String tenantId, boolean isActive);
	
}
