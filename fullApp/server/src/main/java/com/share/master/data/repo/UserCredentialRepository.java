package com.share.master.data.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.User;
import com.base.domain.UserCredential;

/**
 * Created by jt on 1/10/17.
 */
public interface UserCredentialRepository extends CrudRepository<UserCredential,String> {
	
	//@Query("select * from sample.user where pkey=1 order by createdOn desc")
	//List<User> findAll();
	UserCredential findById(String id);
	void deleteById(String id);
	List<UserCredential> findByTenantId(String id);
	void delete(UserCredential user);
	void deleteByTenantIdAndId(String pkey,String id);
	UserCredential findByUsername(String userName);

}
