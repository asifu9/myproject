package com.share.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.TimeDifference;
import com.share.domain.Group;
import com.share.exception.AppException;
import com.share.master.data.service.UserService;
import com.share.photo.service.PhotoService;
import com.share.repositories.GroupRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository repo;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PhotoService photoService;
    
    @Autowired
    MongoTemplate template;

    @Autowired
    public GroupServiceImpl(GroupRepository f)throws AppException {
        this.repo = f;
    }


    @Override
    public List<Group> listAll(String pkey) throws AppException{
        List<Group> products = this.repo.findByTenantId(pkey);
        List<Group> finalList=new ArrayList<>();
        if(products!=null && !products.isEmpty()){
        	for(Group i :products)
        		finalList.add(getOtherData(i));

        }
        return products;
    }
    
    private Group getOtherData(Group g)throws AppException{
    	if(g.getProfilePhotoId()!=null){
    		g.setProfilePhoto(photoService.getById(g.getProfilePhotoId()));
    	}
    	if(g.getPhotos()!=null && g.getPhotos().size()>0){
    		for(String p:g.getPhotos()){
    			g.getPhotoList().add(photoService.getById(p));
    		}
    	}
    	if(g.getCreatedBy()!=null){
    		g.setCreatedByUser(userService.getByIdWithPhoto(g.getCreatedBy()));
    	}
    	g.setCreatedOnStr(new TimeDifference(Calendar.getInstance().getTime(), g.getCreatedOn()).getDifferenceString());
    	
    	return g;
    }
    

    @Override
    public Group getById(String id)throws AppException {
        return repo.findById(id);
    }

    @Override
    public Group saveOrUpdate(Group feed)throws AppException {
        repo.save(feed);
        return feed;
    }


	@Override
	public List<Group> listByCreatedByAndPkey(String userId,String pkey) throws AppException{
		return repo.findByCreatedByAndTenantId(userId,pkey);
	}


	@Override
	public Group getByName(String name) throws AppException{
		return repo.findByGroupUniqueName(name);
	}
 
	@Override
	public synchronized void update(Query query, Update update)throws AppException {
		// TODO Auto-generated method stub
		template.updateFirst(query, update, Group.class);
	}

}
