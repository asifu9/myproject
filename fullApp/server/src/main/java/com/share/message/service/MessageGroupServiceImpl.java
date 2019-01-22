package com.share.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.MessageGroup;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.message.repo.MessageGroupRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class MessageGroupServiceImpl implements MessageGroupService {

    private MessageGroupRepository messageRepo;

    @Autowired
    public MessageGroupServiceImpl(MessageGroupRepository feedCommentsRepository) throws AppException{
        this.messageRepo = feedCommentsRepository;
    }


	@Override
	public MessageGroup saveOrUpdate(MessageGroup message) throws AppException{
		String referenceId=Utils.getReferenceId("MESSAGE-GROUP");
		try{
		return this.messageRepo.save(message);
		} catch (Exception ex) {
			throw new UnableToPersistException("message-group-001", "Error while saving/updating Message Group details " , referenceId, ex,new Gson().toJson(message));
		}
	}

	@Override
	public List<MessageGroup> listAllByPkey(String pkey)throws AppException {
		String referenceId=Utils.getReferenceId("MESSAGE-GROUP");
		try{
		return this.messageRepo.findByTenantId(pkey);
		} catch (Exception ex) {
			throw new UnableToReadException("message-group-002", "Error while fetching Message Group details  by company id " + pkey , referenceId, ex);
		}
	}


	@Override
	public MessageGroup findById(String id)throws AppException {
		String referenceId=Utils.getReferenceId("MESSAGE-GROUP");
		try{
		 return this.messageRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("message-group-003", "Error while fetching Message Group details  by id " + id , referenceId, ex);
		}
	}

 
}
