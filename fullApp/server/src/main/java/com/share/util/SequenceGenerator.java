package com.share.util;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.share.domain.SequenceGen;
import com.share.master.data.service.SequenceGenService;

@Service
public class SequenceGenerator {

	@Value("${sequence-names}")
	private String sequenceList;

	@Value("${sequence-event}")
	private String sequenceEvent;
	@Value("${isSequenceEnabled}")
	private boolean isSequenceEnabled;

	@Autowired
	private SequenceGenService service;

	private final Map<String, FixLengthIDSequence> SEQUENCES = new HashMap<>();

	public SequenceGenerator() {
	}

	public synchronized String getNextValue(String item,String clientId){
		if(!isSequenceEnabled){
			return UUID.randomUUID().toString();
		}
		FixLengthIDSequence obj= SEQUENCES.get(item+"___"+clientId);
		String newValue="";
		try{
		if(obj==null){
		    SequenceGen seq=service.findByUniqueId(item+"___"+clientId);
		    if(seq==null){
		    	//create new entry
		    	seq=new SequenceGen();
				seq.setCreatedOn(Calendar.getInstance().getTime());
				seq.setName(item);
				seq.setTenantId(clientId);
				seq.setUniqueId(item+"___"+clientId);
				seq.setCACHEVALUE(1);
				seq.setMINVALUE(1);
				seq.setMAXVALUE(999999999);
				seq.setValue(1);
				service.saveOrUpdate(seq);
				obj=new FixLengthIDSequence(item+"___"+clientId, item, 0, 0, 999999999, 1);
				SEQUENCES.put(item+"___"+clientId, obj);
		    }else{
		    	obj=new FixLengthIDSequence(seq);
		    	SEQUENCES.put(item+"___"+clientId, obj);
		    }
		}
		newValue= obj.nextVal();
		saveData(item,clientId,obj);
		}catch(Exception exx){
			newValue=UUID.randomUUID().toString();
		}
		return newValue;
	}

	private Collection<FixLengthIDSequence> getMaps() {
		return SEQUENCES.values();
	}

	public void saveData(String item, String clientId, FixLengthIDSequence newValue) {
		SequenceGen gen = service.findByUniqueId(item + "___" + clientId);
		gen.setValue(newValue.getSEQ_ID().longValue());
		gen.setlNextSnapshot(newValue.getlNextSnapshot());
		service.saveOrUpdate(gen);
	}

	public void saveData() {
		System.out.println("sequences size " + new Gson().toJson(SEQUENCES));
		for (FixLengthIDSequence i : getMaps()) {
			System.out.println("ok updating the records");
			SequenceGen gen = service.findByUniqueId(i.getUnqiueName());
			if (gen != null) {
				gen.setValue(i.getSEQ_ID().longValue());
				gen.setCACHEVALUE(i.getCACHEVALUE());
				gen.setlNextSnapshot(i.getlNextSnapshot());
				gen.setUpdatedOn(Calendar.getInstance().getTime());
				service.saveOrUpdate(gen);
			}
		}
	}

}