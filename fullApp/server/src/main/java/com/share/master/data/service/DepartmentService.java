package com.share.master.data.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Department;
import com.base.domain.Designation;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface DepartmentService {

    List<Department> getByTenantId(String tenantId)throws AppException;

    Department getById(String id)throws AppException;
    

    Department saveOrUpdate(Department product)throws AppException;

    void delete(String id)throws AppException;
    
	void update(Query query, Update update)throws AppException;

}
