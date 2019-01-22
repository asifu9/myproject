package com.share.message.service;

import java.util.List;

import com.share.domain.MessageGroup;
import com.share.domain.Messages;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface MessageGroupService {



    MessageGroup saveOrUpdate(MessageGroup message)throws AppException;

    
    List<MessageGroup> listAllByPkey(String pkey)throws AppException;
    
    MessageGroup findById(String id)throws AppException;

	

}
