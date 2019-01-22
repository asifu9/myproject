package com.share.master.data.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Designation;
import com.share.domain.core.UserProjectMap;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface UserProjectMapService {

    List<UserProjectMap> getByTenantId(String tenantId) throws AppException;

    UserProjectMap getById(String id)throws AppException;
    
    List<UserProjectMap> getByTenantIdAndUserId(String tenantId,String userId)throws AppException;
    
    List<UserProjectMap> getByTenantIdAndProjectId(String tenantId,String projectId)throws AppException;

    UserProjectMap saveOrUpdate(UserProjectMap product)throws AppException;

    void delete(String id)throws AppException;
	
	void update(Query query, Update update);

}
