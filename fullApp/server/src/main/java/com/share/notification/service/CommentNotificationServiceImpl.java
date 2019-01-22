package com.share.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.notification.CommentNotification;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.notification.repo.CommentNotificationRepository;

/**
 * Created by jt on 1/10/17.
 */
public class CommentNotificationServiceImpl implements CommentNotificationService {

    private CommentNotificationRepository feedCommentsRepository;

    @Autowired
    public CommentNotificationServiceImpl(CommentNotificationRepository feedCommentsRepository){
        this.feedCommentsRepository = feedCommentsRepository;
    }



    @Override
    public CommentNotification getById(String id)throws AppException {
    	String referenceId=Utils.getReferenceId("COMMENT-NOTIFICATION");
    	try{
    		return feedCommentsRepository.findById(id);
    	} catch (Exception ex) {
			throw new UnableToReadException("comment-notify-001", "Error while reading comment notification details for id " + id , referenceId, ex);
		}
    }

    @Override
    public CommentNotification saveOrUpdate(CommentNotification feed) throws AppException{
    	String referenceId=Utils.getReferenceId("COMMENT-NOTIFICATION");
    	try{
        return feedCommentsRepository.save(feed);
    	} catch (Exception ex) {
			throw new UnableToPersistException("comment-notify-002", "Error while saving/updating comment notification details " , referenceId, ex,new Gson().toJson(feed));
		}

    }

    @Override
    public void delete(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("COMMENT-NOTIFICATION");
    	try{
    
        feedCommentsRepository.delete(id);
    	} catch (Exception ex) {
			throw new UnableToReadException("comment-notify-003", "Error while deleting comment notification details for id " + id , referenceId, ex);
		}

    }
    

    
    


	@Override
	public List<CommentNotification> listBySourceUserId(String userId,int status) throws AppException{
		String referenceId=Utils.getReferenceId("COMMENT-NOTIFICATION");
    	try{
		return feedCommentsRepository.findBySourceUserIdAndStatus(userId, status);
    	} catch (Exception ex) {
			throw new UnableToReadException("comment-notify-004", "Error while reading comment notification details for user id " + userId +
					" status " + status , referenceId, ex);
		}
	}




	@Override
	public List<CommentNotification> listByDestinationUserId(String userId,int status) throws AppException{
		String referenceId=Utils.getReferenceId("COMMENT-NOTIFICATION");
    	try{
		return feedCommentsRepository.findByDestinationUserIdAndStatus(userId, status);
    	} catch (Exception ex) {
			throw new UnableToReadException("comment-notify-005", "Error while reading  destination comment notification details for user id " +
					userId + " status " + status , referenceId, ex);
		}
	}

 
}
