package com.share.custom.form.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Form;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.custom.form.repo.FormRepository;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class FormServiceImpl implements FormService {

	@Autowired
	private FormRepository repo;

	@Override
	public List<Form> getByTenantId(String tenantId)throws AppException {
		String referenceId=Utils.getReferenceId("FORM");
		try{
			return repo.findByTenantId(tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("form-001", "Error while reading Form details by company id " + tenantId , referenceId, ex);
		}
	}

	@Override
	public Form getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("FORM");
		try{
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("form-002", "Error while reading Form details by id " + id , referenceId, ex);
		}
	}

	@Override
	public Form getByName(String name)throws AppException {
		String referenceId=Utils.getReferenceId("FORM");
		try{
			return repo.findByName(name);
		} catch (Exception ex) {
			throw new UnableToReadException("form-003", "Error while reading Form details by form name " + name , referenceId, ex);
		}
	}

	@Override
	public Form saveOrUpdate(Form form)throws AppException {
		String referenceId=Utils.getReferenceId("FORM");
		try{
			return repo.save(form);
		} catch (Exception ex) {
			throw new UnableToPersistException("form-004", "Error while saving/updating Form details" , referenceId, ex,new Gson().toJson(form));
		}
	}

	@Override
	public void update(Query query, Update update)throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void createNewForm(String collectionName, Object data) throws AppException{



	}











}
