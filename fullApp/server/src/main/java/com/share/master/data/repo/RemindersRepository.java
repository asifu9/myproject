package com.share.master.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.Reminders;

public interface RemindersRepository extends CrudRepository<Reminders, String>{

	
	Reminders findByTenantIdAndTypeAndTypeValue(String tenantId,String type,String typeValue);
	Reminders findById(String id);
	
}
