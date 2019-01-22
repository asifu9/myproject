package com.share.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.PollAnswer;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.repositories.PollAnswerRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class PollAnswerServiceImpl implements PollAnswerService {

	private PollAnswerRepository pollQuestionRepo;

	@Autowired
	public PollAnswerServiceImpl(PollAnswerRepository feedCommentsRepository) throws AppException{
		this.pollQuestionRepo = feedCommentsRepository;
	}



	@Override
	public PollAnswer getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("POLL-ANSWER");
		try{
			return pollQuestionRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("poll-001", "Error while fetching Poll details by id " + id , referenceId, ex);
		}
	}

	@Override
	public void delete(String id) throws AppException{
		String referenceId=Utils.getReferenceId("POLL-ANSWER");
		try{
			pollQuestionRepo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("poll-002", "Error while deleting Poll details by id " + id , referenceId, ex);
		}

	}

	@Override
	public PollAnswer saveOrUpdate(PollAnswer feed) throws AppException{
		String referenceId=Utils.getReferenceId("POLL-ANSWER");
		try{
			pollQuestionRepo.save(feed);
			return feed;
		} catch (Exception ex) {
			throw new UnableToPersistException("poll-003", "Error while saving/updating Poll details ", referenceId, ex ,new Gson().toJson(feed));
		}
	}



}
