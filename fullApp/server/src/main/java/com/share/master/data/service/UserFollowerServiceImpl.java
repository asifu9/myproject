package com.share.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.UserFollower;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.UserFollowerRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class UserFollowerServiceImpl implements UserFollowerService {

    private UserFollowerRepository userFollowerRepo;

    @Autowired
    public UserFollowerServiceImpl(UserFollowerRepository feedCommentsRepository) throws AppException{
        this.userFollowerRepo = feedCommentsRepository;
    }



    @Override
    public UserFollower getById(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("USER-FOLLOWER");
		try{
        return userFollowerRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("user-follower-001", "Error while fetching User Follower details by id " + id , referenceId, ex);
		}
    }

    @Override
    public UserFollower saveOrUpdate(UserFollower feed) throws AppException{
    	String referenceId=Utils.getReferenceId("USER-FOLLOWER");
		try{
        userFollowerRepo.save(feed);
        return feed;
		} catch (Exception ex) {
			throw new UnableToPersistException("user-follower-002", "Error while saving/updating User Follower details ", referenceId, ex,new Gson().toJson(feed));
		}
    }

 
}
