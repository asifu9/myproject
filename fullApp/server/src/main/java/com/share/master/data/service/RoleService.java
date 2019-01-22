package com.share.master.data.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.domain.Activities;
import com.base.domain.ConfigActivities;
import com.base.domain.Roles;
import com.share.exception.AppException;
/**
 * Created by jt on 1/10/17.
 */

public interface RoleService {


    Roles getById(String id)throws AppException;

    Roles saveOrUpdate(Roles activities)throws AppException;
    
    List<Roles> getTenantIdAndActive(String tenantId,int isActive)throws AppException;

	void delete(String id)throws AppException;


}
