package com.share.services;

import java.util.List;

import com.share.domain.Updates;
import com.share.exception.AppException;
/**
 * Created by jt on 1/10/17.
 */
public interface UpdateService {

    Updates getById(String id)throws AppException;

    Updates save(Updates obj)throws AppException;

    void delete(String id)throws AppException;
    
    List<Updates> listByUpdatedBy(String userId)throws AppException;
    
    List<Updates> listByUpdatedTo(String userId)throws AppException;

}
