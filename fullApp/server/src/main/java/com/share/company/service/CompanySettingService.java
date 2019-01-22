package com.share.company.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.share.domain.CompanySetting;
import com.share.domain.Group;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface CompanySettingService {


	CompanySetting getById(String id)throws AppException;

	CompanySetting saveOrUpdate(CompanySetting feed)throws AppException;


	void update(Query query, Update update)throws AppException;

}
