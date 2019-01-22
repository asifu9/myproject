package com.share.master.data.service;

import com.share.domain.UserFollower;
import com.share.exception.AppException;
/**
 * Created by jt on 1/10/17.
 */
public interface UserFollowerService {


    UserFollower getById(String id)throws AppException;

    UserFollower saveOrUpdate(UserFollower  userFollower)throws AppException;

}
