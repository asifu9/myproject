package com.share.master.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.base.domain.User;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.UserRepository;
import com.share.photo.service.PhotoService;
import com.share.exception.AppException;
/**
 * Created by jt on 1/10/17.
 */
@Service
@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PhotoService photoService;

	@Autowired
	MongoTemplate template;

	@Autowired
	public UserServiceImpl() {
	}


	@Override
	public User getById(String id) throws UnableToReadException {
		String referenceId = Utils.getReferenceId("USER");
		try {

			return userRepository.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("ERR-001", "Error while fetching user for id " + id, referenceId, ex);
		}
	}

	@Override
	public User getByIdWithPhoto(String id) throws UnableToReadException {
		String referenceId = Utils.getReferenceId("USER");
		User s = null;
		try {
			s = userRepository.findById(id);
			if (s != null && s.getPhotoId() != null) {
				s.setPhoto(photoService.getById(s.getPhotoId()));
			}
		} catch (Exception ex) {
			throw new UnableToReadException("ERR-001", "Error while fetching user for id " + id, referenceId, ex);
		}
		return s;
	}

	@Override
	public User saveOrUpdate(User product) throws UnableToPersistException {
		String referenceId = Utils.getReferenceId("USER");
		try {
			userRepository.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("ERR-001", "Error while saving/updating user details", referenceId, ex,new Gson().toJson(product));
		}
		return product;
	}

	@Override
	public void delete(String id) throws UnableToDeleteException {
		String referenceId = Utils.getReferenceId("USER");
		try {
			userRepository.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("ERR-001", "Error while deleting user with id " + id, referenceId, ex);
		}

	}



	@Override
	public List<User> listByWallId(String id) throws AppException{
		return userRepository.findByTenantId(id);
	}

	@Override
	public void update(Query query, Update update) throws AppException{
		// TODO Auto-generated method stub
		template.updateFirst(query, update, User.class);

	}

	@Override
	public User getByIdOnFewFields(String id) throws AppException{
		return userRepository.findByIdOnSpecificFields(id);
	}

	@Override
	public List<User> searchByName(String name) throws AppException{

		return userRepository.findByNameRegex(".*" + name + ".*");
	}

}
