package com.share.services;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.share.domain.Group;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface GroupService {

	List<Group> listAll(String pkey)throws AppException;

	Group getById(String id)throws AppException;

	Group saveOrUpdate(Group feed)throws AppException;

	Group getByName(String name)throws AppException;

	List<Group> listByCreatedByAndPkey(String userId, String pkey)throws AppException;

	void update(Query query, Update update)throws AppException;

}
