package com.share.message.service;

import java.util.List;

import com.share.domain.MessageChannel;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface MessageChannelService {


    MessageChannel getById(String id)throws AppException;

    MessageChannel saveOrUpdate(MessageChannel message)throws AppException;

    
    List<MessageChannel> listByCreatedBy(String createdBy,String tenantId)throws AppException;
    
    List<MessageChannel> listByTenantId(String tenantId)throws AppException;

	List<MessageChannel> searchByName(String name)throws AppException;


}
