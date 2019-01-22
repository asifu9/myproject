package com.share.company.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Features;
import com.share.domain.core.FormAttachment;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface FeaturesService {

	Features getByName(String name) throws AppException;


	Features getById(String id) throws AppException;


	Features saveOrUpdate(Features features) throws AppException;

	void delete(Features id) throws AppException;



	void update(Query query, Update update);


	List<Features> getAll() throws AppException;

}
