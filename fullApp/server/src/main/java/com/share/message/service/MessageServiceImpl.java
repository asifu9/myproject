package com.share.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.Messages;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.message.repo.MessageRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class MessageServiceImpl implements MessageService {

	private MessageRepository messageRepo;

	@Autowired
	private MongoTemplate template;

	@Autowired
	public MessageServiceImpl(MessageRepository feedCommentsRepository){
		this.messageRepo = feedCommentsRepository;
	}


	@Override
	public Messages getById(String id)throws AppException{
		String referenceId=Utils.getReferenceId("MESSAGE");
		try{
			return messageRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("message-001", "Error while fetching Message details by id " + id , referenceId, ex);
		}
	}

	@Override
	public Messages saveOrUpdate(Messages message) throws AppException{
		String referenceId=Utils.getReferenceId("MESSAGE");
		try{
			messageRepo.save(message);
			return message;
		} catch (Exception ex) {
			throw new UnableToPersistException("message-002", "Error while saving/updating Message details" , referenceId, ex,new Gson().toJson(message));
		}
	}


	@Override
	public List<Messages> listByMessagedTo(String messagedTo,String pkey)throws AppException {
		String referenceId=Utils.getReferenceId("MESSAGE");
		try{
			return messageRepo.findByMessagedToAndTenantId(messagedTo,pkey);
		} catch (Exception ex) {
			throw new UnableToReadException("message-003", "Error while fetching Message details by message To  user id " + 
					messagedTo + " company id " + pkey , referenceId, ex);
		}
	}



	@Override
	public List<Messages> listByMessagedBy(String messagedBy,String pkey)throws AppException {
		String referenceId=Utils.getReferenceId("MESSAGE");
		try{
			return messageRepo.findByMessagedByAndTenantId(messagedBy,pkey);
		} catch (Exception ex) {
			throw new UnableToReadException("message-004", "Error while fetching Message details by message By  user id " + 
					messagedBy + " company id " + pkey , referenceId, ex);
		}
	}

	@Override
	public List<Messages> listByUsers(String userId,String destinationUserId)throws AppException{
		Criteria criteria = new Criteria();
		String referenceId=Utils.getReferenceId("MESSAGE");
		try{
			criteria.orOperator(Criteria.where("messagedBy").is(userId).and("messagedTo").is(destinationUserId),
					Criteria.where("messagedBy").is(destinationUserId).and("messagedTo").is(userId));
			//criteria.andOperator(Criteria.where("messagedBy").is(userId),Criteria.where("messagedTo").is(destinationUserId));

			Query query = new Query(criteria);

			return template.find(query, Messages.class);
		} catch (Exception ex) {
			throw new UnableToReadException("message-005", "Error while fetching Message details by user id " + 
					userId + " and destination user id " + destinationUserId , referenceId, ex);
		}
	}


	@Override
	public List<Messages> listByChannelId(String channelId) throws AppException{
		String referenceId=Utils.getReferenceId("MESSAGE");
		try{
			return messageRepo.findByGroupId(channelId);
		} catch (Exception ex) {
			throw new UnableToReadException("message-006", "Error while fetching Message details by channel id " + 
					channelId , referenceId, ex);
		}
	}


	@Override
	public void delete(Messages message)throws AppException {
		String referenceId=Utils.getReferenceId("MESSAGE");
		try{
			messageRepo.delete(message);
		} catch (Exception ex) {
			throw new UnableToDeleteException("message-007", "Error while deleting Message details by id " + 
					message.getId() , referenceId, ex);
		}
	}


}
