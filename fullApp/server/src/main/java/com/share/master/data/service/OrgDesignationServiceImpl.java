package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Designation;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.DesignationRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class OrgDesignationServiceImpl implements OrgDesignationService {

	@Autowired
    private DesignationRepository repo;

	@Override
	public List<Designation> getByTenantId(String tenantId) throws AppException{
		String referenceId = Utils.getReferenceId("DESIGNATION");
		try{
			return repo.findByTenantId(tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("designation-001", "Error while fetching designation for tenantId " + tenantId , referenceId, ex);
		}
	}

	@Override
	public Designation getById(String id) throws AppException{
		String referenceId = Utils.getReferenceId("DESIGNATION");
		try {
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("designation-002", "Error while fetching designation by id " + id , referenceId, ex);
		}
	}

	@Override
	public Designation saveOrUpdate(Designation product) throws AppException{
		String referenceId = Utils.getReferenceId("DESIGNATION");
		try{
			return repo.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("designation-003", "Error while saving/updating designation  " , referenceId, ex, new Gson().toJson(product));
		}
	}

	@Override
	public void delete(String id) throws AppException{
		String referenceId = Utils.getReferenceId("DESIGNATION");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("designation-004", "Error while deleting designation for id  " + id , referenceId, ex);
		}
		
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Designation user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub
		
	}

	

   



 
}
