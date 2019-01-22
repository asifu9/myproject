package com.share.services;

import java.util.List;

import com.base.domain.Events;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface EventService {

    List<Events> listAll()throws AppException;

    Events getById(String id)throws AppException;

    Events saveOrUpdate(Events feed)throws AppException;

    void delete(String id)throws AppException;
    
    List<Events> listByCreatedBy(String userId)throws AppException;
    
    List<Events> listByTenantIdAndCreatedBy(String tenantId,String createdBy)throws AppException;


}
