package com.share.master.data.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Designation;
import com.share.domain.core.UserDesignationMap;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface UserDesignationMapService {

    List<UserDesignationMap> getByTenantId(String tenantId) throws AppException;

    UserDesignationMap getById(String id)throws AppException;
    
    List<UserDesignationMap> getByTenantIdAndUserId(String tenantId,String userId)throws AppException;
    
    List<UserDesignationMap> getByTenantIdAndDesignationId(String tenantId,String designationId)throws AppException;

    UserDesignationMap saveOrUpdate(UserDesignationMap product)throws AppException;

    void delete(String id)throws AppException;
	
	void update(Query query, Update update);

}
