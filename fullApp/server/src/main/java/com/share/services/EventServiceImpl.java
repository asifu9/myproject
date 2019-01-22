package com.share.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.domain.Events;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.EventRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class EventServiceImpl implements EventService {

	private EventRepository feedRepository;

	@Autowired
	public EventServiceImpl(EventRepository productRepository)throws AppException {
		this.feedRepository = productRepository;
	}


	@Override
	public List<Events> listAll() throws AppException{

		List<Events> products = new ArrayList<>();
		String referenceId=Utils.getReferenceId("EVENT");
		try{
			feedRepository.findAll().forEach(products::add); //fun with Java 8
		} catch (Exception ex) {
			throw new UnableToReadException("event-001", "Error while reading all Events" , referenceId, ex);
		}
		return products;
	}

	@Override
	public Events getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("EVENT");
		try{
			return feedRepository.findById(id);
		}catch (Exception ex) {
			throw new UnableToReadException("event-002", "Error while reading Event for id " + id , referenceId, ex);
		}
	}

	@Override
	public Events saveOrUpdate(Events feed)throws AppException {
		String referenceId=Utils.getReferenceId("EVENT");
		try{
			return feedRepository.save(feed);
		}catch (Exception ex) {
			throw new UnableToPersistException("event-003", "Error while saving/updating Event " , referenceId, ex,new Gson().toJson(feed));
		}
	}

	@Override
	public void delete(String id)throws AppException {
		String referenceId=Utils.getReferenceId("EVENT");
		try{
			feedRepository.delete(id);
		}catch (Exception ex) {
			throw new UnableToDeleteException("event-004", "Error while deleting Event for id " + id , referenceId, ex);
		}
	}



	@Override
	public List<Events> listByCreatedBy(String userId)throws AppException {
		String referenceId=Utils.getReferenceId("EVENT");
		try{
			return feedRepository.findByCreatedBy(userId);
		}catch (Exception ex) {
			throw new UnableToReadException("event-005", "Error while reading Event for user id created by  " + userId , referenceId, ex);
		}
	}



	@Override
	public List<Events> listByTenantIdAndCreatedBy(String tenantId, String createdBy) throws AppException{
		String referenceId=Utils.getReferenceId("EVENT");
		try{
			return feedRepository.findByTenantIdAndCreatedBy(tenantId, createdBy);
		}catch (Exception ex) {
			throw new UnableToReadException("event-006", "Error while reading Event for company id " + 
					tenantId + " and created by " + createdBy , referenceId, ex);
		}
	}


}
