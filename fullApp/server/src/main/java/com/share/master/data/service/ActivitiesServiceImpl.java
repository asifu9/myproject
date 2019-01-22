package com.share.master.data.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.domain.Activities;
import com.base.domain.ConfigActivities;
import com.share.api.DateTimeCal;
import com.share.exception.AppException;
import com.share.master.data.repo.ActivitiesRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class ActivitiesServiceImpl implements ActivityService {

	@Autowired
    private ActivitiesRepository repo;

	@Override
	public Activities getById(String id) throws AppException{
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Activities saveOrUpdate(Activities activities)throws AppException {
		// TODO Auto-generated method stub+
		System.out.println("repo is activitiy "+repo );
		return repo.save(activities);
	}

	@Override
	public List<Activities> getTenantIdAndActive(String tenantId, boolean isActive) throws AppException{
		// TODO Auto-generated method stub
		return repo.findByTenantIdAndIsActive(tenantId, isActive);
	}

	@Override
	public Activities createUpdate(List<ConfigActivities> list, String userId,Activities currentAct,Boolean defaultValue) throws AppException{
		// TODO Auto-generated method stub
		Activities act=currentAct;
		if(act.getActivities()==null){
			act.setCreatedBy(currentAct.getCreatedBy());
			act.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		}else{
			act.setUpdatedBy(currentAct.getUpdatedBy());
			act.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		}
		 
		if(list!=null && list.size()>0){
			for(ConfigActivities item:list){
				if(act.getActivities()==null){
					Map<String,Map<String,Boolean>> temp=new HashMap<>();
					Map<String,Boolean> rules=item.getConfigActivity();
					if(defaultValue!=null){
						for(Entry<String, Boolean> obj:rules.entrySet()){
							obj.setValue(defaultValue);
						}
					}
					temp.put(item.getName(), rules);
					if(item.getName()!=null){
						act.setActivities(temp);
					}
				}else{
					Map<String,Map<String,Boolean>> temp=act.getActivities();
					if(temp!=null && temp.get(item.getName())!=null){
						//check for new items
						Map<String,Boolean> orginalItems = temp.get(item.getName());
						for(String i:item.getConfigActivity().keySet()){
							if(!orginalItems.containsKey(i)){
								if(defaultValue!=null){
									orginalItems.put(i, defaultValue);
								}else{
									orginalItems.put(i, item.getConfigActivity().get(i));
								}
								
							}
						}
					}else{
						if(item.getName()!=null){
							Map<String,Boolean> res=item.getConfigActivity();
							if(defaultValue!=null){
								for(Entry<String, Boolean> obj:res.entrySet()){
									obj.setValue(defaultValue);
								}
							}
							temp.put(item.getName(), res);
							act.setActivities(temp); 
						}
						
					}
					
				}
			}
		}
		return act;
	}


 
}
