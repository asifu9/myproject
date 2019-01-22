package com.share.custom.form.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.Form;

public interface FormRepository extends CrudRepository<Form, String>{

	
	List<Form> findByTenantId(String tenantId);
	Form findById(String id);
	Form findByName(String name);
	
	
}
