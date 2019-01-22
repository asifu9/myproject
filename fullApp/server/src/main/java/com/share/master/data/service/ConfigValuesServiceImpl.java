package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.domain.ConfigValues;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.ConfigValuesRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class ConfigValuesServiceImpl implements ConfigValuesService {

	@Autowired
    private ConfigValuesRepository repo;

	@Override
	public List<ConfigValues> getByTenandTypeActive(String tenantId,String type, boolean isActive,String locale) throws AppException{
		String referenceId=Utils.getReferenceId("CONFIG-VALUES");
		try{
		return repo.findByTenantIdAndTypeAndIsActiveAndLocale(tenantId, type, isActive,locale);
		} catch (Exception ex) {
			throw new UnableToReadException("config-values-001", "Error while reading Config Values details for company id " + 
					tenantId + " type " + type + " is Active " + isActive + " locale "  + locale, referenceId, ex);
		}
	}

	@Override
	public ConfigValues saveOrUpdate(ConfigValues config) throws AppException{
		String referenceId=Utils.getReferenceId("CONFIG-VALUES");
		try{
			return repo.save(config);
		} catch (Exception ex) {
			throw new UnableToPersistException("config-values-002", "Error while saving/updating Config Values details", referenceId, ex,new Gson().toJson(config));
		}
	}

	@Override
	public ConfigValues getByTenandTypeId(String tenantId, String type, String id) throws AppException{
		String referenceId=Utils.getReferenceId("CONFIG-VALUES");
		try{
		return repo.findByTenantIdAndTypeAndId(tenantId, type, id) ;
		} catch (Exception ex) {
			throw new UnableToReadException("config-values-003", "Error while reading Config Values details for company id " + 
					tenantId + " type " + type + "  and id  " + id , referenceId, ex);
		}
	}

	@Override
	public ConfigValues getById(String id)throws AppException {
		String referenceId=Utils.getReferenceId("CONFIG-VALUES");
		try{
		return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("config-values-004", "Error while reading Config Values details for id " + 
					id, referenceId, ex);
		}
	}

	@Override
	public List<ConfigValues> getByTenant(String tenantId, boolean isActive) throws AppException{
		String referenceId=Utils.getReferenceId("CONFIG-VALUES");
		try{
		return repo.findByTenantIdAndIsActive(tenantId,isActive);
		} catch (Exception ex) {
			throw new UnableToReadException("config-values-005", "Error while reading Config Values details for company id " + 
					tenantId + " is Active ", referenceId, ex);
		}
	}

 
}
