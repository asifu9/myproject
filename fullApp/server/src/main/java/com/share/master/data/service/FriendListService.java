package com.share.master.data.service;

import java.util.List;

import com.share.domain.FriendList;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface FriendListService {



    FriendList getById(String id)throws AppException;

    FriendList saveOrUpdate(FriendList feed)throws AppException;

    
	
	

}
