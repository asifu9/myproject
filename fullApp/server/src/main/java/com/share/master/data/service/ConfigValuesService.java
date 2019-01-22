package com.share.master.data.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.domain.ConfigValues;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface ConfigValuesService {


    
    List<ConfigValues> getByTenandTypeActive(String tenantId,String type,boolean isActive,String locale)throws AppException;

    ConfigValues saveOrUpdate(ConfigValues product)throws AppException;
    
    ConfigValues getByTenandTypeId(String tenantId,String type,String id)throws AppException;

    ConfigValues getById(String id)throws AppException;

	List<ConfigValues> getByTenant(String tenantId, boolean isActive)throws AppException;

}
