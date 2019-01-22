package com.share.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.Tag;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.repositories.TagRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class TagServiceImpl implements TagService {

	private TagRepository repo;

	@Autowired
	public TagServiceImpl(TagRepository f) throws AppException{
		this.repo = f;
	}


	@Override
	public Tag getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("TAG");
		try{
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("tag-001", "Error while fetching Tag details by id " + id , referenceId, ex);
		}
	}

	@Override
	public Tag saveOrUpdate(Tag tag) throws AppException{
		String referenceId=Utils.getReferenceId("TAG");
		try{
			return repo.save(tag);
		} catch (Exception ex) {
			throw new UnableToPersistException("tag-002", "Error while saving/updating Tag details " , referenceId, ex,new Gson().toJson(tag));
		}

	}

	@Override
	public void delete(String id) throws AppException{
		String referenceId=Utils.getReferenceId("TAG");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("tag-003", "Error while deleting Tag details by id " + id , referenceId, ex);
		}

	}





	@Override
	public List<Tag> listByObjectId(String objectId) throws AppException{
		String referenceId=Utils.getReferenceId("TAG");
		try{
			return repo.findByObjectId(objectId);
		} catch (Exception ex) {
			throw new UnableToReadException("tag-004", "Error while fetching Tag details by object id " + objectId , referenceId, ex);
		}
	}




	@Override
	public List<Tag> listByUserId(String userId) throws AppException{
		String referenceId=Utils.getReferenceId("TAG");
		try{
			return repo.findByUserId(userId);
		} catch (Exception ex) {
			throw new UnableToReadException("tag-005", "Error while fetching Tag details by user id " + userId , referenceId, ex);
		}

	}



}
