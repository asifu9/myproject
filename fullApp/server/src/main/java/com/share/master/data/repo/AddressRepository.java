package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.Address;


public interface AddressRepository extends CrudRepository<Address, String>{

	
	List<Address> findByUserId(String userId);
	Address findById(String id);
	
}
