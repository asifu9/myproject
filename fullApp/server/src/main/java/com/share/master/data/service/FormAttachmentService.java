package com.share.master.data.service;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.share.domain.core.FormAttachment;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface FormAttachmentService {

	FormAttachment getByTenantIdAndFormId(String tenantId,String formId) throws AppException;

	FormAttachment getById(String id) throws AppException;
    

	FormAttachment saveOrUpdate(FormAttachment formAttachment) throws AppException;

    void delete(String id) throws AppException;
    
	
	void update(Query query, Update update);

}
