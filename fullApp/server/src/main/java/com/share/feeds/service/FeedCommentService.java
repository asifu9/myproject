package com.share.feeds.service;

import java.util.List;

import com.share.domain.FeedComments;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface FeedCommentService {

    List<FeedComments> listAll()throws AppException;

    FeedComments getById(String id)throws AppException;

    FeedComments saveOrUpdate(FeedComments feed)throws AppException;

    void delete(String id)throws AppException;

}
