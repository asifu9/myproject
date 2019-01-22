package com.share.feeds.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.FeedLikes;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.feeds.repo.FeedLikesRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class FeedLikeServiceImpl implements FeedLikeService {

	private FeedLikesRepository feedLikesRepository;

	@Autowired
	public FeedLikeServiceImpl(FeedLikesRepository feedCommentsRepository)throws AppException {
		this.feedLikesRepository = feedCommentsRepository;
	}



	@Override
	public FeedLikes getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("FEED-LIKE");
		try{
			return feedLikesRepository.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("feed-like-001", "Error while reading Feed Like details by id " + id , referenceId, ex);
		}
	}

	@Override
	public FeedLikes saveOrUpdate(FeedLikes feed)throws AppException {
		String referenceId=Utils.getReferenceId("FEED-LIKE");
		try{
			return feedLikesRepository.save(feed);
		} catch (Exception ex) {
			throw new UnableToPersistException("feed-like-002", "Error while saving/updating Feed Like details", referenceId, ex,new Gson().toJson(feed));
		}
	}

	@Override
	public void delete(String id) throws AppException{
		String referenceId=Utils.getReferenceId("FEED-LIKE");
		try{
			feedLikesRepository.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("feed-like-003", "Error while deleting Feed Like details by id " + id , referenceId, ex);
		}
	}



	@Override
	public List<FeedLikes> listByLikedBy(String likedBy) throws AppException{
		String referenceId=Utils.getReferenceId("FEED-LIKE");
		try{
			return feedLikesRepository.findByLikedBy(likedBy);
		} catch (Exception ex) {
			throw new UnableToReadException("feed-like-004", "Error while reading Feed Like details by  liked by user id " + likedBy , referenceId, ex);
		}
	}


}
