package com.share.services;

import java.util.List;

import com.share.domain.PollQuestion;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface PollQuestionService {


    PollQuestion getById(String id)throws AppException;

    PollQuestion saveOrUpdate(PollQuestion feed)throws AppException;

    void delete(String id)throws AppException;
    
    List<PollQuestion> listByCreatedBy(String pollId)throws AppException;
    


}
