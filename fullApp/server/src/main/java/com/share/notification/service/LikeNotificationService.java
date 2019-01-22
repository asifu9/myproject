package com.share.notification.service;

import java.util.List;

import com.share.domain.notification.LikeNotification;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface LikeNotificationService {


    LikeNotification getById(String id)throws AppException;

    LikeNotification saveOrUpdate(LikeNotification feed)throws AppException;

    void delete(String id)throws AppException;
    
    List<LikeNotification> listBySourceUserId(String userId,int status)throws AppException;
    
    List<LikeNotification> listByDestinationUserId(String userId,int status)throws AppException;


}
