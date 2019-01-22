package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.Events;

public interface EventRepository extends CrudRepository<Events, String>{

	
	List<Events> findByCreatedBy(String userId);
	List<Events> findByTenantIdAndCreatedBy(String tenantId,String userId);
	Events findById(String id);
	
	void deleteById(String id);
	void delete(Events user);
	void deleteByTenantIdAndId(String TenantId,String id);
}
