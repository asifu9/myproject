package com.share.master.data.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.base.domain.UserCredential;
/**
 * Created by jt on 1/10/17.
 */
import com.share.exception.AppException;
public interface UserCredentialService {

	UserCredential login(String name)throws AppException;
	
	UserCredential createUpdate(UserCredential cre)throws AppException;
	
	UserCredential getById(String id)throws AppException;
 
}
