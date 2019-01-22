package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.FriendRequest;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.FriendRequestRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private FriendRequestRepository userRepository;
    

    @Autowired
    public FriendRequestServiceImpl(FriendRequestRepository productRepository)throws AppException {
        this.userRepository = productRepository;
    }



    @Override
    public FriendRequest getById(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("FRIEND-REQUEST");
		try{
        return userRepository.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("friend-request-001", "Error while reading Friend Request details by id " + id , referenceId, ex);
		}
    }

    @Override
    public FriendRequest saveOrUpdate(FriendRequest product)throws AppException {
    	String referenceId=Utils.getReferenceId("FRIEND-REQUEST");
		try{
    	return  userRepository.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("friend-request-002", "Error while saving/updating Friend Request details " , referenceId, ex,new Gson().toJson(product));
		}
    }



	@Override
	public List<FriendRequest> listByRquestFrom(String userId, String pkey, String status)throws AppException {
		String referenceId=Utils.getReferenceId("FRIEND-REQUEST");
		try{
			return userRepository.findByRequestedFromAndTenantIdAndStatusAndActive(userId, pkey, status,true);
		} catch (Exception ex) {
			throw new UnableToReadException("friend-request-003", "Error while reading Friend Request details by  user Id " + userId +
					" company id " + pkey + " status " + status, referenceId, ex);
		}
	}



	@Override
	public List<FriendRequest> listByRequestTo(String userId, String pkey, String status) throws AppException{
		String referenceId=Utils.getReferenceId("FRIEND-REQUEST");
		try{
		return userRepository.findByRequestedToAndTenantIdAndStatusAndActive(userId, pkey, status,true);
		} catch (Exception ex) {
			throw new UnableToReadException("friend-request-004", "Error while reading Friend Request details by requested to user " + userId
					+" company id " + pkey + "  status " + status, referenceId, ex);
		}
	}



	@Override
	public List<FriendRequest> listByRquestFrom(String currentUserId, String wallId) throws AppException{
		String referenceId=Utils.getReferenceId("FRIEND-REQUEST");
		try{
		return userRepository.findByRequestedFromAndTenantIdAndActive(currentUserId, wallId,true);
		} catch (Exception ex) {
			throw new UnableToReadException("friend-request-005", "Error while reading Friend Request details by requested from  user " + currentUserId
					+" company id " + wallId , referenceId, ex);
		}
	}

	@Override
	public List<FriendRequest> listByRquestTo(String currentUserId, String wallId)throws AppException {
		String referenceId=Utils.getReferenceId("FRIEND-REQUEST");
		try{
		return userRepository.findByRequestedToAndTenantIdAndActive(currentUserId, wallId,true);
		} catch (Exception ex) {
			throw new UnableToReadException("friend-request-006", "Error while reading Friend Request details by requested to  user " + currentUserId
					+" company id " + wallId , referenceId, ex);
		}
	}

	@Override
	public List<FriendRequest> listByRquestTo(String userId, String pkey, String status)throws AppException {
		String referenceId=Utils.getReferenceId("FRIEND-REQUEST");
		try{
		return userRepository.findByRequestedToAndTenantIdAndStatusAndActive(userId, pkey, status,true);
		} catch (Exception ex) {
			throw new UnableToReadException("friend-request-006", "Error while reading Friend Request details by requested to  user " + userId
					+" company id " + pkey + " status " + status , referenceId, ex);
		}
	}


 
}
