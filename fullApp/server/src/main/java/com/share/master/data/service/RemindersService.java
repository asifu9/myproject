package com.share.master.data.service;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.share.domain.core.Reminders;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface RemindersService {

    Reminders getByTenantIdAndTypeAndTypeValue(String tenantId,String type,String typeValue) throws AppException;

    Reminders getById(String id) throws AppException;
    

    Reminders saveOrUpdate(Reminders reminders) throws AppException;
     void delete(String id)  throws AppException;
	
	void update(Query query, Update update) throws AppException;

}
