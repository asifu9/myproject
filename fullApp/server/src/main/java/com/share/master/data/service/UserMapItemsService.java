package com.share.master.data.service;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.share.domain.UserMapItems;
import com.share.exception.AppException;
/**
 * Created by jt on 1/10/17.
 */
public interface UserMapItemsService {


	UserMapItems getById(String id)throws AppException;

    UserMapItems saveOrUpdate(UserMapItems message)throws AppException;

	void executeUpdate(Query query, Update update6)throws AppException;


}
