package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.domain.Address;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.AddressRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepo;

	@Autowired
	public AddressServiceImpl(AddressRepository productRepository){
		this.addressRepo = productRepository;
	}



	@Override
	public Address getById(String id)throws AppException {
		String referenceId=Utils.getReferenceId("ADDRESS");
		try{
			return addressRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("address-001", "Error while reading address details for id " + id , referenceId, ex);
		}
	}

	@Override
	public Address saveOrUpdate(Address product) throws AppException{
		String referenceId=Utils.getReferenceId("ADDRESS");
		try{
			return addressRepo.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("address-002", "Error while saving/updating address details " , referenceId, ex,new Gson().toJson(product));
		}

	}


	@Override
	public List<Address> getByUserId(String userId) throws AppException{
		String referenceId=Utils.getReferenceId("ADDRESS");
		try{

			return addressRepo.findByUserId(userId);
		} catch (Exception ex) {
			throw new UnableToReadException("address-003", "Error while reading address details for user id " + userId , referenceId, ex);
		}

	}



	@Override
	public void delete(String id) throws AppException {
		String referenceId=Utils.getReferenceId("ADDRESS");
		try{

			addressRepo.delete(id);
		} catch (Exception ex) {
			throw new UnableToReadException("address-004", "Error while deleting address details for id " + id , referenceId, ex);
		}

	}


}
