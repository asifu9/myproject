package com.share.custom.form.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Form;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */

public interface FormService {

    List<Form> getByTenantId(String tenantId)throws AppException;

    Form getById(String id)throws AppException;
    Form getByName(String name)throws AppException;

    Form saveOrUpdate(Form form)throws AppException;

	
	void update(Query query, Update update)throws AppException;
	
	void createNewForm(String collectionName,Object data)throws AppException;

}
