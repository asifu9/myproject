package com.share.master.data.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.base.domain.User;



/**
 * Created by jt on 1/10/17.
 */
public interface UserRepository extends CrudRepository<User,String> {
	
	//@Query("select * from sample.user where pkey=1 order by createdOn desc")
	//List<User> findAll();
	User findById(String id);
	void deleteById(String id);
	List<User> findByTenantId(String id);
	void delete(User user);
	void deleteByTenantIdAndId(String pkey,String id);
	@Query(value="{ 'id' : ?0 }",fields="{ 'id' : 1, 'name' : 1,'photoPath':1,'emailId':1,'userName':1}")
	User findByIdOnSpecificFields(String id);
	
	
	 List<User> findByNameRegex(String name);

}
