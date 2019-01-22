package com.share.notification.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.base.domain.Column;
import com.base.domain.MetaData;
import com.base.domain.ReportConfig;
import com.share.api.DateTimeCal;
import com.share.custom.form.service.FormService;
import com.share.custom.form.service.ReportConfigService;
import com.share.domain.core.LeaveApply;
import com.share.domain.core.Notification;
import com.share.exception.AppException;
import com.share.leave.service.LeaveApplyService;
import com.share.master.data.service.UserService;
import com.share.notification.service.NotificationService;

@RestController
@RequestMapping("/notificationapi")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationApi {

	@Autowired
	private NotificationService service;

	@Autowired
	private UserService userService;

	@Autowired
	private LeaveApplyService leaveApplyService;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Notification> getById(@PathVariable(value = "id") String id)throws AppException {
		MultiValueMap<String, String> headers = new HttpHeaders();
		Notification list = service.findById(id);
		System.out.println(list);

		return new ResponseEntity<Notification>(list, headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Object> getByName(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "status") String status) throws AppException {
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Notification> list = service.findByUserIdAndStatus(userId, Integer.parseInt(status));
		Map<String, Object> map = new HashMap<>();
		if (list != null && list.size() > 0) {

			try {
				for (Notification i : list) {
					if (!map.containsKey(i.getDestinationId())) {
						try {
							map.put(i.getDestinationId(), userService.getByIdWithPhoto(i.getDestinationId()));
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					if (!map.containsKey(i.getSourceId())) {
						try {
							map.put(i.getSourceId(), userService.getByIdWithPhoto(i.getSourceId()));
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (i.getType().equals("Leave")) {
						i.setValueObject(leaveApplyService.getById(i.getValueId()));
					}
				}
			} catch (Exception exx) {
				throw exx;
			}
		}

		System.out.println(list);
		Map<String, Object> result = new HashMap<>();
		result.put("notification", list);
		result.put("users", map);

		return new ResponseEntity<Object>(result, headers, HttpStatus.OK);
	}

}
