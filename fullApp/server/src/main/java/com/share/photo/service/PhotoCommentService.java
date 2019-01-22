package com.share.photo.service;

import java.util.List;

import com.share.domain.PhotoComments;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface PhotoCommentService {


    PhotoComments getById(String id)throws AppException;

    PhotoComments saveOrUpdate(PhotoComments feed)throws AppException;

    void delete(String id)throws AppException;

    


	
	

}
