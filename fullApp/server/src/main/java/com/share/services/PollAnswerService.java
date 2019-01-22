package com.share.services;

import com.share.domain.PollAnswer;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface PollAnswerService {


    PollAnswer getById(String id)throws AppException;


    void delete(String id)throws AppException;
    


	PollAnswer saveOrUpdate(PollAnswer feed)throws AppException; 
	
	

}
