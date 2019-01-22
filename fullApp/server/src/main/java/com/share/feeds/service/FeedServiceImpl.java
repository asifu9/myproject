package com.share.feeds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.Feed;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.feeds.repo.FeedsRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class FeedServiceImpl implements FeedService {

	private FeedsRepository feedRepository;

	@Autowired
	MongoTemplate template;

	@Autowired
	public FeedServiceImpl(FeedsRepository productRepository) throws AppException{
		this.feedRepository = productRepository;
	}


	@Override
	public Feed getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("FEED");
		try{
			return feedRepository.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("feed-001", "Error while reading Feed details by id " + id , referenceId, ex);
		}
	}

	@Override
	public Feed saveOrUpdate(Feed feed) throws AppException{
		String referenceId=Utils.getReferenceId("FEED");
		try{
			return feedRepository.save(feed);
		} catch (Exception ex) {
			throw new UnableToPersistException("feed-002", "Error while saving Feed details ", referenceId, ex,new Gson().toJson(feed));
		}
	}

	@Override
	public void delete(String id)throws AppException {
		String referenceId=Utils.getReferenceId("FEED");
		try{
			feedRepository.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("feed-003", "Error while deleting Feed details by id " + id , referenceId, ex);
		}
	}




	@Override
	public List<Feed> listByUserId(String cteatedBy)throws AppException {
		String referenceId=Utils.getReferenceId("FEED");
		try{
			return feedRepository.findByCreatedBy(cteatedBy);
		} catch (Exception ex) {
			throw new UnableToReadException("feed-004", "Error while reading Feed details by created By user " + cteatedBy , referenceId, ex);
		}
	}







	@Override
	public synchronized void update(Query query, Update update) throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("FEED");
		try{
			template.updateFirst(query, update, Feed.class);
		} catch (Exception ex) {
			throw new UnableToPersistException("feed-005", "Error while updating Feed details - query" + new Gson().toJson(query), referenceId, ex,new Gson().toJson(update));
		}
	}


	@Override
	public void saveOrUpdateDoc(Feed feed,String collectionName) throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("FEED");
		try{
			template.save(feed, collectionName);
		} catch (Exception ex) {
			throw new UnableToPersistException("feed-006", "Error while saving/updating Feed details for collection name " + collectionName , referenceId, ex,new Gson().toJson(feed));
		}
	}





	@Override
	public List<Feed> listAll(String wallId) throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("FEED");
		try{
			return template.findAll(Feed.class,wallId);
		} catch (Exception ex) {
			throw new UnableToReadException("feed-007", "Error while reading Feed details by wall id " +wallId  , referenceId, ex);
		}
	}


	@Override
	public Feed getById(String id, String wallId) throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("FEED");
		try{
			return template.findById(id, Feed.class, wallId);
		} catch (Exception ex) {
			throw new UnableToReadException("feed-008", "Error while reading Feed details by id " + id , referenceId, ex);
		}
	}




}
