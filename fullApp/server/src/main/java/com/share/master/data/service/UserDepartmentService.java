package com.share.master.data.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.share.domain.core.UserDepartmentManage;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface UserDepartmentService {

    
	
//

    List<UserDepartmentManage> listAll(String tenantId) throws AppException;

    UserDepartmentManage getById(String id)throws AppException;
    
    List<UserDepartmentManage> getByDepartmentIdAndTenantId(String departmentId,String tenantId)throws AppException;
    
    List<UserDepartmentManage> getByUserIdAndTenantId(String departmentId,String tenantId)throws AppException;
    
    UserDepartmentManage getByDepartmentIdAndUserIdAndTenantId(String departmentId,String userId,String tenantId)throws AppException;
    
    UserDepartmentManage saveOrUpdate(UserDepartmentManage product)throws AppException;

    void delete(String id)throws AppException;
    

	void update(Query query, Update update);

}
