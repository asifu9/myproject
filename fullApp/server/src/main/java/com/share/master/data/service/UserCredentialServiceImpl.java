package com.share.master.data.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.base.domain.UserCredential;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.UserCredentialRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class UserCredentialServiceImpl implements UserCredentialService {

	
	@Autowired
    private UserCredentialRepository repo;
	


	@Override
	public UserCredential login(String name) throws AppException{
		String referenceId=Utils.getReferenceId("USER-CREDENTIAL");
		try{
			return repo.findByUsername(name);
		} catch (Exception ex) {
			throw new UnableToReadException("user-credential-001", "Error while fetching user credential details by name " + name , referenceId, ex);
		}
	}

	@Override
	public UserCredential createUpdate(UserCredential cre) throws AppException{
		String referenceId=Utils.getReferenceId("USER-CREDENTIAL");
		try{
		return repo.save(cre);
		} catch (Exception ex) {
			throw new UnableToPersistException("user-credential-002", "Error while saving/updating user credential details " , referenceId, ex,new Gson().toJson(cre));
		}
	}

	@Override
	public UserCredential getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("USER-CREDENTIAL");
		try{
		return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("user-credential-003", "Error while fetching user credential details by id " + id , referenceId, ex);
		}
	}




  

 
}
