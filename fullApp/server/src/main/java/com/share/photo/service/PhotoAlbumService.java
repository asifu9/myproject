package com.share.photo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.domain.PhotoAlbum;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */

public interface PhotoAlbumService {


    PhotoAlbum getById(String id)throws AppException;

    PhotoAlbum saveOrUpdate(PhotoAlbum feed)throws AppException;

    void delete(String id)throws AppException;
    
    List<PhotoAlbum> listByCreatedBy(String userId)throws AppException;
    

}
