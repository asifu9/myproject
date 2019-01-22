package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.base.domain.Activities;

@Service
public interface ActivitiesRepository extends CrudRepository<Activities, String>{

	
	List<Activities> findByTenantIdAndIsActive(String TenantId,boolean isActive);
	
	Activities findById(String id);
	
}