package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.Designation;





public interface DesignationRepository extends CrudRepository<Designation, String>{

	
	List<Designation> findByTenantId(String tenantId);
	Designation findById(String id);
	
}
