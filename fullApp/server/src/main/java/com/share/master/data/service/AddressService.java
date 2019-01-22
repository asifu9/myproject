package com.share.master.data.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.domain.Address;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */

public interface AddressService {


    Address getById(String id)throws AppException;

    Address saveOrUpdate(Address product)throws AppException;

	List<Address> getByUserId(String userId)throws AppException;
	
	void delete(String id)throws AppException;

}
