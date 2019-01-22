package com.share.master.data.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.share.domain.SequenceGen;
import com.share.domain.core.Team;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface SequenceGenService {

    List<SequenceGen> getByTenantId(String tenantId);
    
    SequenceGen findByUniqueId(String tenantId);

    SequenceGen getById(String id);
    
    
    
    SequenceGen saveOrUpdate(SequenceGen product);

    void delete(String id);
    

	void deleteById(String id);
	
	void delete(SequenceGen user); 
	
	
	void update(Query query, Update update);

}
