package com.share.leave.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.LeaveTypes;

public interface LeaveTypesRepository extends CrudRepository<LeaveTypes, String>{

	
	List<LeaveTypes> findByCompanyId(String tenantId);
	LeaveTypes findById(String id);
	
}
