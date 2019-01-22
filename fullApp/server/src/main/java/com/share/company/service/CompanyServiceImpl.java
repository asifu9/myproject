package com.share.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Company;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.company.repo.CompanyRepository;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class CompanyServiceImpl implements CompanyService {


	private CompanyRepository companyRepo;

	@Autowired
	public CompanyServiceImpl(CompanyRepository feedCommentsRepository)throws AppException {
		this.companyRepo = feedCommentsRepository;
	}

	@Override
	public List<Company> listAll() throws AppException{
		String referenceId=Utils.getReferenceId("COMPANY");
		try{
			return this.companyRepo.findByIsActive(1);
		} catch (Exception ex) {
			throw new UnableToReadException("company-001", "Error while fetching all companies details ", referenceId, ex);
		}
	}

	@Override
	public Company getById(String id)throws AppException {
		String referenceId=Utils.getReferenceId("COMPANY");
		try{
			return this.companyRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("company-002", "Error while fetching company details for id " + id, referenceId, ex);
		}
	}

	@Override
	public Company saveOrUpdate(Company product)throws AppException{
		String referenceId=Utils.getReferenceId("COMPANY");
		try{
			return this.companyRepo.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("company-003", "Error while saving/upading company details ", referenceId, ex,new Gson().toJson(product));
		}
	}

	@Override
	public void delete(String id)throws AppException {
		String referenceId=Utils.getReferenceId("COMPANY");
		try{
			this.companyRepo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("company-004", "Error while delete  companies detail for id "  + id, referenceId, ex);
		}

	}



	@Override
	public void update(Query query, Update update) throws AppException{
		// TODO Auto-generated method stub

	}

	@Override
	public Company getByUniqueName(String uname) throws AppException{
		String referenceId=Utils.getReferenceId("COMPANY");
		try{
			return companyRepo.findByUname(uname);
		} catch (Exception ex) {
			throw new UnableToReadException("company-005", "Error while fetching company detail for unique name " + uname, referenceId, ex);
		}

	}





}
