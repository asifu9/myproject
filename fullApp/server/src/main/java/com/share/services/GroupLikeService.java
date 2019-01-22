package com.share.services;

import java.util.List;

import com.share.domain.FeedLikes;
import com.share.domain.GroupLikes;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface GroupLikeService {

    List<GroupLikes> listAll()throws AppException;

    GroupLikes getById(String id)throws AppException;

    GroupLikes saveOrUpdate(GroupLikes feed)throws AppException;

    void delete(String id)throws AppException;
    
    
    List<GroupLikes> listByLikedBy(String commentedBy)throws AppException;

	void deleteById(String id)throws AppException;
	
	void delete(FeedLikes feed)throws AppException; 
	
	

}
