package com.share.feeds.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.share.domain.Feed;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface FeedService {

 
    
    List<Feed> listAll(String wallId)throws AppException;

    Feed getById(String id)throws AppException;
    
    Feed getById(String id,String wallId)throws AppException;
    

    Feed saveOrUpdate(Feed feed)throws AppException;

    void delete(String id)throws AppException;
    
    List<Feed> listByUserId(String id)throws AppException;
	
	void update(Query query, Update update)throws AppException;

	void saveOrUpdateDoc(Feed feed, String collectionName)throws AppException;
	
	
}
