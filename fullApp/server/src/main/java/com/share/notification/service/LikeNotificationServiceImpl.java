package com.share.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.notification.LikeNotification;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.notification.repo.LikeNotificationRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class LikeNotificationServiceImpl implements LikeNotificationService {

	private LikeNotificationRepository feedCommentsRepository;

	@Autowired
	public LikeNotificationServiceImpl(LikeNotificationRepository feedCommentsRepository) throws AppException{
		this.feedCommentsRepository = feedCommentsRepository;
	}



	@Override
	public LikeNotification getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("LIKE-NOTIFICATION");
		try{
			return feedCommentsRepository.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("like-notification-001", "Error while reading Like Notification details by id " + id , referenceId, ex);
		}
	}

	@Override
	public LikeNotification saveOrUpdate(LikeNotification feed) throws AppException{

		String referenceId=Utils.getReferenceId("LIKE-NOTIFICATION");
		try{
			feedCommentsRepository.save(feed);
			return feed;
		} catch (Exception ex) {
			throw new UnableToPersistException("like-notification-002", "Error while saving/updating Like Notification details" , referenceId, ex,new Gson().toJson(feed));
		}
	}

	@Override
	public void delete(String id)throws AppException {
		String referenceId=Utils.getReferenceId("LIKE-NOTIFICATION");
		try{
			feedCommentsRepository.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("like-notification-003", "Error while deleting Like Notification details by id " + id , referenceId, ex);
		}

	}






	@Override
	public List<LikeNotification> listBySourceUserId(String userId,int status) throws AppException{
		String referenceId=Utils.getReferenceId("LIKE-NOTIFICATION");
		try{
			return feedCommentsRepository.findBySourceUserIdAndStatus(userId, status);
		} catch (Exception ex) {
			throw new UnableToReadException("like-notification-004", "Error while reading Like Notification details by source user id "
					+ userId + " status " + status , referenceId, ex);
		}
	}




	@Override
	public List<LikeNotification> listByDestinationUserId(String userId,int status) throws AppException{
		String referenceId=Utils.getReferenceId("LIKE-NOTIFICATION");
		try{
			return feedCommentsRepository.findByDestinationUserIdAndStatus(userId, status);
		} catch (Exception ex) {
			throw new UnableToReadException("like-notification-005", "Error while reading Like Notification details by destination user id " + 
					userId + "  status " + status , referenceId, ex);
		}
	}


}
