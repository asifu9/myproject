package com.share.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.share.domain.notification.CommentNotification;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface CommentNotificationService {


    CommentNotification getById(String id)throws AppException;

    CommentNotification saveOrUpdate(CommentNotification feed)throws AppException;

    void delete(String id)throws AppException;
    
    List<CommentNotification> listBySourceUserId(String userId,int status)throws AppException;
    
    List<CommentNotification> listByDestinationUserId(String userId,int status)throws AppException;

	
	

}
