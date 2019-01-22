package com.share.custom.form.repo;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.FormAttachment;

public interface FormAttachmentRepository extends CrudRepository<FormAttachment, String>{

	
	FormAttachment findByTenantIdAndFormId(String tenantId,String formId);
	
	FormAttachment findById(String id);
	
}
