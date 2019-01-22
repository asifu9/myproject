package com.share.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.MessageChannel;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.message.repo.MessageChannelRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class MessageChannelServiceImpl implements MessageChannelService {

	@Autowired
    private MessageChannelRepository repo;

    @Autowired
    private MongoTemplate template;

	@Override
	public MessageChannel getById(String id)throws AppException {

		String referenceId=Utils.getReferenceId("MANAGE-CHANNEL");
		try{
		return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("manage-channel-001", "Error while reading Manage Channel details by id " + id , referenceId, ex);
		}
	}

	@Override
	public MessageChannel saveOrUpdate(MessageChannel message) throws AppException{

		String referenceId=Utils.getReferenceId("MANAGE-CHANNEL");
		try{
		return repo.save(message);
		} catch (Exception ex) {
			throw new UnableToPersistException("manage-channel-002", "Error while saving/updating Manage Channel details ", referenceId, ex,new Gson().toJson(message));
		}
	}

	@Override
	public List<MessageChannel> listByCreatedBy(String createdBy, String tenantId) throws AppException{

		String referenceId=Utils.getReferenceId("MANAGE-CHANNEL");
		try{
		return repo.findByCreatedByAndTenantId(createdBy, tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("manage-channel-003", "Error while reading Manage Channel details by created by user  " + 
					createdBy + " company id " + tenantId , referenceId, ex);
		}
	}

	@Override
	public List<MessageChannel> listByTenantId(String tenantId) throws AppException{

		String referenceId=Utils.getReferenceId("MANAGE-CHANNEL");
		try{
		return repo.findByTenantId(tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("manage-channel-004", "Error while reading Manage Channel details by " + 
					 " company id " + tenantId , referenceId, ex);
		}
	}

	@Override
	public List<MessageChannel> searchByName(String name) throws AppException{

		String referenceId=Utils.getReferenceId("MANAGE-CHANNEL");
		try{
		
		return repo.findByNameRegex(".*"+name+".*");
		} catch (Exception ex) {
			throw new UnableToReadException("manage-channel-005", "Error while reading Manage Channel details by name " + 
					name , referenceId, ex);
		}

	}

 
}
