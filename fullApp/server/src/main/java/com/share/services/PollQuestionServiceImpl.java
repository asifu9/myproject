package com.share.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.PollQuestion;
import com.share.repositories.PollQuestionRepository;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class PollQuestionServiceImpl implements PollQuestionService {

    private PollQuestionRepository pollQuestionRepo;

    @Autowired
    public PollQuestionServiceImpl(PollQuestionRepository feedCommentsRepository) throws AppException{
        this.pollQuestionRepo = feedCommentsRepository;
    }



    @Override
    public PollQuestion getById(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("POLL-QUESTION");
		try{
    	return pollQuestionRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("poll-question-001", "Error while fetching Poll Question details by id " + id , referenceId, ex);
		}
    }

    @Override
    public PollQuestion saveOrUpdate(PollQuestion feed) throws AppException{
    	String referenceId=Utils.getReferenceId("POLL-QUESTION");
		try{
    	pollQuestionRepo.save(feed);
        return feed;
		} catch (Exception ex) {
			throw new UnableToPersistException("poll-question-002", "Error while saving/updating Poll Question details", referenceId, ex,new Gson().toJson(feed));
		}
    }

    @Override
    public void delete(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("POLL-QUESTION");
		try{
    	pollQuestionRepo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("poll-question-003", "Error while deleting Poll Question details by id " + id , referenceId, ex);
		}

    }

	@Override
	public List<PollQuestion> listByCreatedBy(String feedId) throws AppException{
		String referenceId=Utils.getReferenceId("POLL-QUESTION");
		try{
		return pollQuestionRepo.findByCreatedBy(feedId);
		} catch (Exception ex) {
			throw new UnableToReadException("poll-question-004", "Error while fetching Poll Question details by feed id " + feedId , referenceId, ex);
		}
	}

 
}
