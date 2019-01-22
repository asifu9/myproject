package com.share.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.Page;
import com.share.repositories.PageRepository;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class PageServiceImpl implements PageService {

    private PageRepository pageRepo;

    @Autowired
    public PageServiceImpl(PageRepository feedCommentsRepository) throws AppException{
        this.pageRepo = feedCommentsRepository;
    }




    @Override
    public Page getById(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("PAGE");
		try{
    	return pageRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("page-001", "Error while fetching Page details by id " + id , referenceId, ex);
		}
    }

    @Override
    public Page saveOrUpdate(Page feed) throws AppException{
    	String referenceId=Utils.getReferenceId("PAGE");
		try{
        pageRepo.save(feed);
        return feed;
		} catch (Exception ex) {
			throw new UnableToPersistException("page-002", "Error while saving/updating Page details" , referenceId, ex,new Gson().toJson(feed));
		}
    }

    @Override
    public void delete(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("PAGE");
		try{
    
        pageRepo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("page-003", "Error while deleting Page details by id " + id , referenceId, ex);
		}

    }
    

	@Override
	public List<Page> listByCreatedBy(String createdBy) throws AppException{
		String referenceId=Utils.getReferenceId("PAGE");
		try{
		return pageRepo.findByCreatedBy(createdBy);
		} catch (Exception ex) {
			throw new UnableToReadException("page-004", "Error while fetching Page details by created by id" +  createdBy, referenceId, ex);
		}

	}
 
}
