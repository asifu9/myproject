package com.share.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.Updates;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.repositories.UpdateRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class UpdateServiceImpl implements UpdateService {

    private UpdateRepository updateRepository;

    @Autowired
    public UpdateServiceImpl(UpdateRepository feedCommentsRepository) throws AppException{
        this.updateRepository = feedCommentsRepository;
    }


    @Override
    public Updates getById(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("UPDATE");
		try{
        return updateRepository.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("update-001", "Error while fetching Update details by id " + id , referenceId, ex);
		}
    }

    @Override
    public Updates save(Updates feed) throws AppException{
    	String referenceId=Utils.getReferenceId("UPDATE");
		try{
    	updateRepository.save(feed);
        return feed;
		} catch (Exception ex) {
			throw new UnableToPersistException("update-002", "Error while saving/updating Update details " , referenceId, ex,new Gson().toJson(feed));
		}
    }

    @Override
    public void delete(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("UPDATE");
		try{
    	updateRepository.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("update-003", "Error while deleting Update details by id " + id , referenceId, ex);
		}

    }

    


	@Override
	public List<Updates> listByUpdatedBy(String userId) throws AppException{
		String referenceId=Utils.getReferenceId("UPDATE");
		try{
		return updateRepository.findByUpdatedBy(userId);
		} catch (Exception ex) {
			throw new UnableToReadException("update-004", "Error while fetching Update details by user updated by id " + userId , referenceId, ex);
		}
	}


	@Override
	public List<Updates> listByUpdatedTo(String userId) throws AppException{
		String referenceId=Utils.getReferenceId("UPDATE");
		try{
		return updateRepository.findByUpdatedTo(userId);
		} catch (Exception ex) {
			throw new UnableToReadException("update-005", "Error while fetching Update details by user updated to id " + userId , referenceId, ex);
		}
	}

 
}
