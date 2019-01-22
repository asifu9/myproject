package com.share.master.data.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Designation;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface OrgDesignationService {

    List<Designation> getByTenantId(String tenantId)throws AppException;

    Designation getById(String id)throws AppException;
    

    Designation saveOrUpdate(Designation product)throws AppException;

    void delete(String id)throws AppException;
    

	void deleteById(String id);
	
	void delete(Designation user); 
	
	
	void update(Query query, Update update);

}
