package com.share.master.data.service;

import com.share.domain.UserFollowedBy;
import com.share.domain.UserFollower;
import com.share.exception.AppException;
/**
 * Created by jt on 1/10/17.
 */
public interface UserFollowedByService {


    UserFollowedBy getById(String id)throws AppException;

    UserFollowedBy saveOrUpdate(UserFollowedBy  userFollower)throws AppException;

}
