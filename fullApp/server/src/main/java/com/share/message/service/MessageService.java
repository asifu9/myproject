package com.share.message.service;

import java.util.List;

import com.share.domain.Messages;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface MessageService {


    Messages getById(String id)throws AppException;

    Messages saveOrUpdate(Messages message)throws AppException;
    void delete(Messages message)throws AppException;
    
    List<Messages> listByMessagedBy(String messagedBy,String pkey)throws AppException;
    
    List<Messages> listByMessagedTo(String messagedTo,String pkey)throws AppException;

	List<Messages> listByUsers(String sourceId,String desgtinatId)throws AppException;
	
	List<Messages> listByChannelId(String channelId)throws AppException;

}
