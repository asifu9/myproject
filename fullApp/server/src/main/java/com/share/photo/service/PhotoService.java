package com.share.photo.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Photo;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface PhotoService {


    Photo getById(String id)throws AppException;

    Photo saveOrUpdate(Photo feed)throws AppException;

    void delete(String id)throws AppException;
    
    List<Photo> listByAlbumId(String userId)throws AppException;
    
    
    List<Photo> listByCreatedBy(String friendId)throws AppException;

	
	
	void update(Query query, Update update)throws AppException;
	
	

}
