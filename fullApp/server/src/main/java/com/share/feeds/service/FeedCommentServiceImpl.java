package com.share.feeds.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.FeedComments;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.feeds.repo.FeedCommentsRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class FeedCommentServiceImpl implements FeedCommentService {

	private FeedCommentsRepository feedCommentsRepository;

	@Autowired
	public FeedCommentServiceImpl(FeedCommentsRepository feedCommentsRepository) throws AppException{
		this.feedCommentsRepository = feedCommentsRepository;
	}


	@Override
	public List<FeedComments> listAll()throws AppException {
		List<FeedComments> products = new ArrayList<>();
		String referenceId=Utils.getReferenceId("FEED-COMMENT");
		try{
			feedCommentsRepository.findAll().forEach(products::add); //fun with Java 8
		} catch (Exception ex) {
			throw new UnableToReadException("feed-comment-001", "Error while reading feed comments for all", referenceId, ex);
		}
		return products;
	}

	@Override
	public FeedComments getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("FEED-COMMENT");
		try{
			return feedCommentsRepository.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("feed-comment-002", "Error while reading feed comments for id " + id, referenceId, ex);
		}
	}

	@Override
	public FeedComments saveOrUpdate(FeedComments feed)throws AppException {
		String referenceId=Utils.getReferenceId("FEED-COMMENT");
		try{
			return feedCommentsRepository.save(feed);
		} catch (Exception ex) {
			throw new UnableToPersistException("feed-comment-003", "Error while saving/updating feed comments ", referenceId, ex,new Gson().toJson(feed));
		}
	}

	@Override
	public void delete(String id) throws AppException{
		String referenceId=Utils.getReferenceId("FEED-COMMENT");
		try{
			feedCommentsRepository.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("feed-comment-004", "Error while deleting feed comments for id " + id, referenceId, ex);
		}

	}


}
