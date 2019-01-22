package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Department;
import com.base.domain.Designation;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.DepartmentRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
    private DepartmentRepository repo;

	@Override
	public List<Department> getByTenantId(String tenantId)throws AppException {
		String referenceId=Utils.getReferenceId("DEPARTMENT");
		try{
		return repo.findByTenantId(tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("department-001", "Error while reading department details for company id " + tenantId , referenceId, ex);
		}
	}

	@Override
	public Department getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("DEPARTMENT");
		try{
		return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("department-002", "Error while reading department details for id " + id , referenceId, ex);
		}
	}

	@Override
	public Department saveOrUpdate(Department product) throws AppException{
		String referenceId=Utils.getReferenceId("DEPARTMENT");
		try{
	
		return repo.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("department-003", "Error while saving/updating department details " , referenceId, ex,new Gson().toJson(product));
		}
	}

	@Override
	public void delete(String id)throws AppException {
		String referenceId=Utils.getReferenceId("DEPARTMENT");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("department-004", "Error while deleting department details for  id " + id , referenceId, ex);
		}
		
	}



	@Override
	public void update(Query query, Update update)throws AppException {
		// TODO Auto-generated method stub
		
	}

	

   



 
}
