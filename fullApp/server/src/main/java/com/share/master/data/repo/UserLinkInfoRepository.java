package com.share.master.data.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.share.domain.UserLinkInfo;

@Service
public interface UserLinkInfoRepository extends CrudRepository<UserLinkInfo, String>{
	UserLinkInfo findById(String id);
}