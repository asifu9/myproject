package com.share.leave.api;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.bouncycastle.crypto.tls.DatagramTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.share.api.DateTimeCal;
import com.share.domain.core.LeaveApply;
import com.share.domain.core.LeaveApplyComment;
import com.share.domain.core.Notification;
import com.share.domain.core.UserLeaves;
import com.share.exception.AppException;
import com.share.leave.service.LeaveApplyService;
import com.share.leave.service.LeaveTypesService;
import com.share.master.data.service.UserLeavesService;
import com.share.master.data.service.UserService;
import com.share.notification.service.NotificationService;

@RestController
@RequestMapping("/leaveapply")
@CrossOrigin(origins = "http://localhost:4200")
public class LeaveApplyApi {

	@Autowired
	private LeaveApplyService leaveApplyService;
	
	@Autowired
	private UserLeavesService userLeaveService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<LeaveApply> create(@RequestBody LeaveApply leaveType)  throws AppException{
		// its a new record to create
		leaveType.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		// its an update record
		// first fetch the data for given id
		leaveType.setId(UUID.randomUUID().toString());
		leaveApplyService.saveOrUpdate(leaveType);
		
		Notification notification=new Notification();
		notification.setCompanyId(leaveType.getCompanyId());
		notification.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		notification.setDestinationId(leaveType.getAssignedTo());
		notification.setSourceId(leaveType.getUserId());
		notification.setDetails("Approve Leave");
		notification.setId(UUID.randomUUID().toString());
		notification.setStatus(1);
		notification.setType("Leave");
		notification.setUrl("need to set");
		notification.setUserId(leaveType.getAssignedTo());
		notification.setValueId(leaveType.getId());
		notificationService.saveOrUpdate(notification);
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<LeaveApply>(leaveType, headers, HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<LeaveApply> update(@RequestBody LeaveApply user)  throws AppException{
		// its a new record to create
		if (user.getStatus().equals("Approved")) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		user.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		// its an update record
		// first fetch the data for given id
		leaveApplyService.saveOrUpdate(user);
		
		Notification notify=notificationService.findByValueId(user.getId());
		notify.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		notify.setStatus(1);
		notificationService.saveOrUpdate(notify);
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<LeaveApply>(user, headers, HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<LeaveApply> getById(@PathVariable(value = "id") String id)  throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		LeaveApply user = leaveApplyService.getById(id);
		if(user.getUserId()!=null){
			user.setUser(userService.getById(user.getUserId()));
		}
		if(user.getAssignedTo()!=null){
			user.setAssignedToUser(userService.getById(user.getAssignedTo()));
		}
		if(user!=null && user.getComments()!=null){
			for(LeaveApplyComment it : user.getComments()){
				if(it.getCommentedBy()!=null){
					it.setCommentedByUser(userService.getById(it.getCommentedBy()));
				}
			}
		}
		return new ResponseEntity<LeaveApply>(user, headers, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<LeaveApply> deleteById(@PathVariable(value = "id") String id) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		LeaveApply app= leaveApplyService.getById(id);
		leaveApplyService.delete(app);
		return new ResponseEntity<LeaveApply>(app, headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<LeaveApply>> getByUserId(@PathVariable(value = "id") String id)  throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<LeaveApply> user = leaveApplyService.getByUserId(id);

		return new ResponseEntity<List<LeaveApply>>(user, headers, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/update/{id}/{status}/{userId}/{notificationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity getByUserIdUpateFromNotify(@PathVariable(value = "id") String id,@PathVariable(value = "status") String status,
			@PathVariable(value = "userId") String userId,@PathVariable(value = "notificationId") String notificationId)  throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		LeaveApply user = leaveApplyService.getById(id);
		UserLeaves userLeaves= userLeaveService.getByUserIdAndFinancialYear(user.getUserId(), user.getFinancialYear());
		Notification notification = notificationService.findById(notificationId);
		notification.setStatus(0);
		if("approved".equalsIgnoreCase(status)){
			user.setStatus("Approved");
			if(userLeaves!=null && userLeaves.getLeaves()!=null){
				userLeaves.getLeaves().forEach(i->{
					if(i.getLeaveName().equalsIgnoreCase(user.getLeaveName())){
						i.setTakenLeaves(i.getTakenLeaves()+1);
						i.setAccumulatedLeaves(i.getAccumulatedLeaves()-1);
					}
				});
			}
		}
		notificationService.saveOrUpdate(notification);
		userLeaveService.saveOrUpdate(userLeaves);
		leaveApplyService.saveOrUpdate(user);
		
		return new ResponseEntity(user, headers, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/update/{id}/{status}/{userId}/{notifyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity approveRejectData(@PathVariable(value = "id") String id,@PathVariable(value = "status") String status,
			@PathVariable(value = "userId") String userId,@PathVariable(value = "notifyId") String notifyId,@RequestBody LeaveApplyComment comment) throws AppException {
		MultiValueMap<String, String> headers = new HttpHeaders();
		LeaveApply user = leaveApplyService.getById(id);
		UserLeaves userLeaves= userLeaveService.getByUserIdAndFinancialYear(user.getUserId(), user.getFinancialYear());
		if("approved".equalsIgnoreCase(status)){
			user.setStatus("Approved");
			if(userLeaves!=null && userLeaves.getLeaves()!=null){
				userLeaves.getLeaves().forEach(i->{
					if(i.getLeaveName().equalsIgnoreCase(user.getLeaveName())){
						i.setTakenLeaves(i.getTakenLeaves()+1);
						i.setAccumulatedLeaves(i.getAccumulatedLeaves()-1);
					}
				});
			}
		}else{
			user.setStatus("Rejected");
		}
		if(comment!=null && comment.getDescription()!=null){
			comment.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			user.getComments().add(comment);
		}
		Notification notification= notificationService.findById(notifyId);
		notification.setStatus(0);
		notificationService.saveOrUpdate(notification);
		userLeaveService.saveOrUpdate(userLeaves);
		leaveApplyService.saveOrUpdate(user);
		
		return new ResponseEntity(user, headers, HttpStatus.OK);
	}
	

	

	@RequestMapping(method = RequestMethod.GET, value = "/user/{id}/{financialYear}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<LeaveApply>> getByUserId(@PathVariable(value = "id") String id,@PathVariable(value = "financialYear") String financialYear)  throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<LeaveApply> user = leaveApplyService.getByUserIdAndYearRange(id, financialYear);

		return new ResponseEntity<List<LeaveApply>>(user, headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/company/{id}/{leaveName}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<LeaveApply>> getByTenantId(@PathVariable(value = "id") String id,@PathVariable(value = "leaveName") String leaveName) throws AppException {
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<LeaveApply> user = leaveApplyService.getByCompanyIdAndLeaveName(id, leaveName);

		return new ResponseEntity<List<LeaveApply>>(user, headers, HttpStatus.OK);
	}

}
