package com.share.master.data.service;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.share.domain.UserLinkInfo;
import com.share.domain.core.FormAttachment;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface UserLinkInfoService {

	UserLinkInfo getByTenantIdAndId(String tenantId,String formId) throws AppException;

	UserLinkInfo getById(String id)throws AppException;
    

	UserLinkInfo saveOrUpdate(UserLinkInfo userLinkInfo)throws AppException;

    void delete(String id)throws AppException;
    
	
	
	void update(Query query, Update update)throws AppException;

}
