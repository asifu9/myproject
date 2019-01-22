package com.share.company.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Company;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface CompanyService {

    List<Company> listAll()throws AppException;

    Company getById(String id)throws AppException;
    

    Company saveOrUpdate(Company product)throws AppException;

    void delete(String id)throws AppException;
    
	void update(Query query, Update update)throws AppException;

	 Company getByUniqueName(String string)throws AppException;

}
