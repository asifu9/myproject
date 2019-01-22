package com.share.master.data.service;

import java.util.List;

import com.base.domain.Activities;
import com.base.domain.ConfigActivities;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */

public interface ActivityService {


    Activities getById(String id)throws AppException;

    Activities saveOrUpdate(Activities activities)throws AppException;
    
    Activities createUpdate(List<ConfigActivities> list,String userId,Activities act,Boolean defaultValue)throws AppException;
    
    List<Activities> getTenantIdAndActive(String tenantId,boolean isActive)throws AppException;


}
