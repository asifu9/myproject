package com.share.leave.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.share.domain.core.LeaveTypes;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface LeaveTypesService {

    List<LeaveTypes> getByCompanyId(String companyId)throws AppException;

    LeaveTypes getById(String id)throws AppException;
    

    LeaveTypes saveOrUpdate(LeaveTypes product)throws AppException;

    void delete(String id)throws AppException;

	
	
	void update(Query query, Update update);

}
