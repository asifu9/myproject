package com.share.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.UserFollowedBy;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.UserFollowedByRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class UserFollowedByServiceImpl implements UserFollowedByService {

	private UserFollowedByRepository userFollowedByRepository;

	@Autowired
	public UserFollowedByServiceImpl(UserFollowedByRepository feedCommentsRepository) throws AppException{
		this.userFollowedByRepository = feedCommentsRepository;
	}



	@Override
	public UserFollowedBy getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("USER-FOLLOWED-BY");
		try{
			return userFollowedByRepository.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("user-followedBy-001", "Error while fetching User Followed By details by id " + id , referenceId, ex);
		}
	}

	@Override
	public UserFollowedBy saveOrUpdate(UserFollowedBy feed) throws AppException{
		String referenceId=Utils.getReferenceId("USER-FOLLOWED-BY");

		try{
			userFollowedByRepository.save(feed);
			return feed;
		} catch (Exception ex) {
			throw new UnableToPersistException("user-followedBy-002", "Error while saving/updating User Followed By details" , referenceId, ex,new Gson().toJson(feed));
		}
	}


}
