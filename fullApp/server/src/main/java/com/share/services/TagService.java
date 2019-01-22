package com.share.services;

import java.util.List;

import com.share.domain.Tag;
import com.share.exception.AppException;
/**
 * Created by jt on 1/10/17.
 */
public interface TagService {

	Tag getById(String id)throws AppException;

	Tag saveOrUpdate(Tag feed)throws AppException;

    void delete(String id)throws AppException;
    
    List<Tag> listByObjectId(String objectId)throws AppException;
    
    List<Tag> listByUserId(String userId)throws AppException;	

}
