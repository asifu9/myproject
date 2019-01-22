package com.share.services;

import java.util.List;

import com.share.domain.Page;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface PageService {


    Page getById(String id)throws AppException;

    Page saveOrUpdate(Page feed)throws AppException;

    void delete(String id)throws AppException;
    
    List<Page> listByCreatedBy(String createdBy)throws AppException;

}
