package com.share.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.Feed;
import com.share.domain.Page;

public interface PageRepository extends CrudRepository<Page, String>{

	List<Page> findByCreatedBy(String createdBy);
	Page findById(String id);
	void deleteById(String id);
	void delete(Page user);
	void deleteByTenantIdAndId(String pkey,String id);
}
