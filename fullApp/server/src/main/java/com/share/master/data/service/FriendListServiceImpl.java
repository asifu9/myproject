package com.share.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.FriendList;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.FriendListRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class FriendListServiceImpl implements FriendListService {

	private FriendListRepository friendListRepo;

	@Autowired
	public FriendListServiceImpl(FriendListRepository feedCommentsRepository)throws AppException {
		this.friendListRepo = feedCommentsRepository;
	}




	@Override
	public FriendList getById(String id)throws AppException {
		String referenceId=Utils.getReferenceId("FRIEND-LIST");
		try{
			return friendListRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("friend-list-001", "Error while reading Friend List details by id " + id , referenceId, ex);
		}
	}

	@Override
	public FriendList saveOrUpdate(FriendList feed) throws AppException{
		String referenceId=Utils.getReferenceId("FRIEND-LIST");
		try{
			return friendListRepo.save(feed);
		} catch (Exception ex) {
			throw new UnableToPersistException("feed-002", "Error while saving Friend List details", referenceId, ex,new Gson().toJson(feed));
		}
	}

}
