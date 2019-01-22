package com.share.feeds.service;

import java.util.List;

import com.share.domain.FeedLikes;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface FeedLikeService {


    FeedLikes getById(String id)throws AppException;

    FeedLikes saveOrUpdate(FeedLikes feed)throws AppException;

    void delete(String id)throws AppException;
    
    
    List<FeedLikes> listByLikedBy(String commentedBy)throws AppException;

	
	
	

}
