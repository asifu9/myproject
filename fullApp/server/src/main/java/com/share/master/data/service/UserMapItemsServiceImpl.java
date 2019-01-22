package com.share.master.data.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.MessageChannel;
import com.share.domain.UserMapItems;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.master.data.repo.UserMapItemRepository;
import com.share.message.repo.MessageChannelRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class UserMapItemsServiceImpl implements UserMapItemsService {

	@Autowired
    private UserMapItemRepository repo;

    @Autowired
    private MongoTemplate template;

	@Override
	public UserMapItems saveOrUpdate(UserMapItems message) throws AppException{
		String referenceId=Utils.getReferenceId("USER-MAP-ITEMS");
		try{
		return repo.save(message);
		} catch (Exception ex) {
			throw new UnableToPersistException("user-map-items-001", "Error while saving/updating User Map Items details ", 
					referenceId, ex ,new Gson().toJson(message));
		}
	}



	@Override
	public UserMapItems getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("USER-MAP-ITEMS");
		try{
				return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("user-map-items-002", "Error while fetching User Map Items details by id " + id , referenceId, ex);
		}
	}

	@Override
	public void executeUpdate(Query query, Update update) throws AppException{
		
		String referenceId=Utils.getReferenceId("USER-MAP-ITEMS");
		try{
		template.findAndModify(query, update, UserMapItems.class);
		} catch (Exception ex) {
			throw new UnableToPersistException("user-map-items-003", "Error while saving/updating User Map Items details query is " + new Gson().toJson(query), 
					referenceId, ex ,new Gson().toJson(update));
		}
		
	}

	
	
	

 
}
