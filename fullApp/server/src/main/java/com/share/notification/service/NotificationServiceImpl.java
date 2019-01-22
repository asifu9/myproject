package com.share.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.core.Notification;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.notification.repo.NotificationRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository repo;

	@Override
	public List<Notification> findByUserId(String userId) throws AppException{
		String referenceId=Utils.getReferenceId("NOTIFICATION");
		try{
			return repo.findByUserId(userId);
		} catch (Exception ex) {
			throw new UnableToReadException("notification-001", "Error while fetching Notification details by user id " + userId , referenceId, ex);
		}
	}

	@Override
	public List<Notification> findByUserIdAndCompanyId(String userId, String companyId) throws AppException{
		String referenceId=Utils.getReferenceId("NOTIFICATION");
		try{
			return repo.findByUserIdAndCompanyId(userId, companyId);
		} catch (Exception ex) {
			throw new UnableToReadException("notification-002", "Error while fetching Notification details by user id " + userId + " company id " + companyId , referenceId, ex);
		}
	}

	@Override
	public List<Notification> findByUserIdAndCompanyIdAndStatus(String userId, String companyId, String status) throws AppException{
		String referenceId=Utils.getReferenceId("NOTIFICATION");
		try{
			return repo.findByUserIdAndCompanyIdAndStatus(userId, companyId, status);
		} catch (Exception ex) {
			throw new UnableToReadException("notification-003", "Error while fetching Notification details by user id " +
					userId + " company id " + companyId + "  status " + status , referenceId, ex);
		}
	}

	@Override
	public Notification findById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("NOTIFICATION");
		try{
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("notification-004", "Error while fetching Notification details by id " +
					id , referenceId, ex);
		}
	}

	@Override
	public List<Notification> findByUserIdAndStatus(String userId, int status) throws AppException{
		String referenceId=Utils.getReferenceId("NOTIFICATION");
		try{
			return repo.findByUserIdAndStatus(userId, status);
		} catch (Exception ex) {
			throw new UnableToReadException("notification-005", "Error while fetching Notification details by user id " +
					userId + " status " + status, referenceId, ex);
		}
	}

	@Override
	public Notification saveOrUpdate(Notification notification) throws AppException{
		String referenceId=Utils.getReferenceId("NOTIFICATION");
		try{
			return repo.save(notification);
		} catch (Exception ex) {
			throw new UnableToPersistException("notification-006", "Error while saving/updating Notification details " , referenceId, ex,new Gson().toJson(notification));
		}
	}

	@Override
	public void deleteById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("NOTIFICATION");
		try{

			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("notification-007", "Error while deleting Notification details by id "+ id , referenceId, ex);
		}
	}

	@Override
	public void update(Query query, Update update) throws AppException{
		// TODO Auto-generated method stub

	}

	@Override
	public Notification getByUniqueName(String string) throws AppException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notification findByValueId(String valueId) throws AppException{
		String referenceId=Utils.getReferenceId("NOTIFICATION");
		try{
			return repo.findByValueId(valueId);
		} catch (Exception ex) {
			throw new UnableToReadException("notification-008", "Error while fetching Notification details by value Id "+ valueId , referenceId, ex);
		}
	}





}
