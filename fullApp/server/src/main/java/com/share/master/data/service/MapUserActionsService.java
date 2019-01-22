package com.share.master.data.service;

import java.util.List;

import com.share.domain.MapUserActions;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface MapUserActionsService {


    MapUserActions getById(String id)throws AppException;

    MapUserActions saveOrUpdate(MapUserActions mapUserActions)throws AppException;

	List<MapUserActions> getByTenantAndType(String tenantId,String type)throws AppException;
	
	void deleteMap (MapUserActions action)throws AppException;
	
	List<MapUserActions> getByTenantAndUserId(String tenantId,String userId)throws AppException;
	
	List<MapUserActions> getByTenantAndTypeAndUser(String tenantId,String type,String user)throws AppException;

	List<MapUserActions> getByTenantAndTypedValueAndType(String tenantId, String typedValue, String type)throws AppException;

}
