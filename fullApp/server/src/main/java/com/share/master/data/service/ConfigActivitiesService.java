package com.share.master.data.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.domain.ConfigActivities;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface ConfigActivitiesService {


    ConfigActivities getById(String id)throws AppException;

    ConfigActivities saveOrUpdate(ConfigActivities product)throws AppException;
    
    List<ConfigActivities> getTenantIdAndActive(String tenantId,boolean isActive)throws AppException;

    ConfigActivities getByTenantAndName(String tenantId,String name)throws AppException;

}
