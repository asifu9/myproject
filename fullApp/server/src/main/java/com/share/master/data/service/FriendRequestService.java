package com.share.master.data.service;

import java.util.List;

import com.share.domain.FriendRequest;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface FriendRequestService {


    FriendRequest getById(String id)throws AppException;
    
    List<FriendRequest> listByRquestFrom(String userId,String pkey,String status)throws AppException; 

    FriendRequest saveOrUpdate(FriendRequest product)throws AppException;

	List<FriendRequest> listByRequestTo(String userId, String pkey, String status)throws AppException;

	List<FriendRequest> listByRquestFrom(String currentUserId, String wallId)throws AppException;
	
	List<FriendRequest> listByRquestTo(String currentUserId, String wallId)throws AppException;

	List<FriendRequest> listByRquestTo(String userId, String pkey, String string)throws AppException;


}
