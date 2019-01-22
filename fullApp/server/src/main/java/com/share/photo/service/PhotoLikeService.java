package com.share.photo.service;

import java.util.List;

import com.base.domain.PhotoLikes;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface PhotoLikeService {


    PhotoLikes getById(String id)throws AppException;

    PhotoLikes saveOrUpdate(PhotoLikes feed)throws AppException;

    void delete(String id)throws AppException;
    
    
    List<PhotoLikes> listByLikedBy(String commentedBy)throws AppException;

	

}
