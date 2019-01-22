package com.share.master.data.repo;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.ActivityAction;

public interface ActivityActionRepository extends CrudRepository<ActivityAction, String>{

	
	List<ActivityAction> findByTenantIdAndIsActive(String TenantId,boolean isActive);
	ActivityAction findById(String id);
	
}
