package com.share.leave.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.core.LeaveTypes;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.leave.repo.LeaveTypesRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class LeaveTypesServiceImpl implements LeaveTypesService {

	@Autowired
	private LeaveTypesRepository repo;

	@Override
	public List<LeaveTypes> getByCompanyId(String company) throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAVE-TYPE");
		try{
			return repo.findByCompanyId(company);
		} catch (Exception ex) {
			throw new UnableToReadException("leave-type-001", "Error while fetching leave types by company Id " + company, referenceId, ex);
		}
	}

	@Override
	public LeaveTypes getById(String id) throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAVE-TYPE");
		try{
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("leave-type-002", "Error while fetching leave types by id " + id, referenceId, ex);
		}
	}

	@Override
	public LeaveTypes saveOrUpdate(LeaveTypes product) throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAVE-TYPE");
		try{
			return repo.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("leave-type-003", "Error while saving/updating leave type details", referenceId, ex,new Gson().toJson(product));
		}
	}

	@Override
	public void delete(String id)throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAVE-TYPE");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("leave-type", "Error while deleting leave type detail for id " + id, referenceId, ex);
		}
	}


	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub

	}




}
