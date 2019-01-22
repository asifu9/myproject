package com.share.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.core.Reminders;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.RemindersRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class RemindersServiceImpl implements RemindersService {

	@Autowired
    private RemindersRepository repo;

	@Override
	public Reminders getByTenantIdAndTypeAndTypeValue(String tenantId, String type, String typeValue)  throws AppException{
		String referenceId=Utils.getReferenceId("REMINDER");
		try{
		return repo.findByTenantIdAndTypeAndTypeValue(tenantId, type, typeValue);
		} catch (Exception ex) {
			throw new UnableToReadException("reminder-001", "Error while fetching reminder details for company id " + tenantId + 
					" type " + type + " typeValue " + typeValue , referenceId, ex);
		}
	}

	@Override
	public Reminders getById(String id) throws AppException {
		String referenceId=Utils.getReferenceId("REMINDER");
		try{
		return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("reminder-002", "Error while fetching reminder detail for id" + id, referenceId, ex);
		}
	}

	@Override
	public Reminders saveOrUpdate(Reminders reminders)  throws AppException{
		String referenceId=Utils.getReferenceId("REMINDER");
		try{
		return repo.save(reminders);
		} catch (Exception ex) {
			throw new UnableToPersistException("reminder-003", "Error while saving/updating reminder details"  , referenceId, ex,new Gson().toJson(reminders) );
		}
	}
	
	@Override
	public void delete(String id)  throws AppException{
		String referenceId=Utils.getReferenceId("REMINDER");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("reminder-004", "Error while deleting reminder details for id " + id  , referenceId, ex);
		}
	}

	@Override
	public void update(Query query, Update update) throws AppException {
		// TODO Auto-generated method stub
		
	}

	

   



 
}
