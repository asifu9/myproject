package com.share.master.data.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.User;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.api.DateTimeCal;
import com.share.company.service.CompanySettingService;
import com.share.domain.CompanySetting;
import com.share.domain.core.LeaveDetails;
import com.share.domain.core.LeaveTypes;
import com.share.domain.core.UserLeaves;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.leave.service.LeaveTypesService;
import com.share.master.data.repo.UserLeavesRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class UserLeavesServiceImpl implements UserLeavesService {

	@Autowired
	private UserLeavesRepository repo;

	@Autowired
	private LeaveTypesService leavesTypeService;

	@Autowired
	private UserService userService;

	@Autowired
	private CompanySettingService companySettingService;

	@Override
	public UserLeaves getById(String id) throws AppException {
		// TODO Auto-generated method stub
		String referenceId = Utils.getReferenceId("LEAVES");
		try {
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("leave-001", "Error while fetching user leave details for id"	 + id, referenceId, ex);
		}
	}

	@Override
	public List<UserLeaves> getByUserId(String id) throws AppException {
		String referenceId = Utils.getReferenceId("LEAVES");
		try {
		return repo.findByUserId(id);
		} catch (Exception ex) {
			throw new UnableToReadException("leave-002", "Error while fetching user leave details for user id " +id, referenceId, ex);
		}
	}

	@Override
	public UserLeaves getByUserIdAndCurrent(String userId, boolean isCurrent) throws AppException {
		String referenceId = Utils.getReferenceId("LEAVES");
		try {
		return repo.findByUserIdAndIsCurrent(userId, isCurrent);
		} catch (Exception ex) {
			throw new UnableToReadException("leave-003", "Error while fetching user leave details for user id " + userId + " is current " + isCurrent, referenceId, ex);
		}
	}

	@Override
	public UserLeaves saveOrUpdate(UserLeaves product) throws AppException {
		
		String referenceId = Utils.getReferenceId("LEAVES");
		try {
			return repo.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("leave-004",
					"Error while saving/updating user leave for object ", referenceId, ex, new Gson().toJson(product));
		}
	}

	@Override
	public void delete(String id) throws AppException {
		String referenceId = Utils.getReferenceId("LEAVES");
		try {
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("leave-005", "Error while deleting user leave for id " + id, referenceId,
					ex);
		}

	}

	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub

	}

	// public static void main(String[] args) {
	// double i= 5.7;
	// double roundedValue = Math.floor(i);
	// System.out.println("rounded value " + roundedValue + " : " + i);
	// double reminder = i-roundedValue;
	// System.out.println("reminder " + reminder);
	// if(reminder>=0.3 && reminder<=0.6){
	// roundedValue+=0.5;
	// }else if(reminder >0.6){
	// roundedValue+=1.0;
	// }
	// System.out.println(roundedValue);
	// }

	double getLeaves(double i) {
		double roundedValue = Math.floor(i);
		System.out.println("rounded value " + roundedValue + " : " + i);
		double reminder = i - roundedValue;
		System.out.println("reminder " + reminder);
		if (reminder >= 0.3 && reminder <= 0.6) {
			roundedValue += 0.5;
		} else if (reminder > 0.6) {
			roundedValue += 1.0;
		}
		return roundedValue;
	}

	@Override
	public UserLeaves createLeavesForUser(String userId, String companyId, int year) throws AppException {
		String referenceId = Utils.getReferenceId("LEAVES");
		
		UserLeaves lastYearLeaves = null;
		UserLeaves currentLeaves = repo.findByUserIdAndYear(userId, year);
		if (currentLeaves != null) {
			return currentLeaves;
		}
		// check last year exist or not
		currentLeaves = repo.findByUserIdAndYear(userId, year - 1);
		if (currentLeaves != null) {
			lastYearLeaves = currentLeaves;
		}

		List<LeaveTypes> leaveTypes = leavesTypeService.getByCompanyId(companyId);
		CompanySetting session = companySettingService.getById(companyId);
		String startMonth = session.getLeaveStartEndMonths();

		// check if alredy created or not
		boolean isFirstTime = false;
		User u = userService.getById(userId);
		Calendar dateOfJoining = Calendar.getInstance();
		if (lastYearLeaves == null && u.getDoj() != 0) {
			dateOfJoining.setTimeInMillis(u.getDoj() * 1000);
			isFirstTime = true;
		}

		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		int difInMonths = 0;
		// cal.set(Calendar.MONTH, getMonthNumber(startMonth));
		if (startMonth.equals("Jan-Dec")) {

			if (isFirstTime) {
				// generate for after joining year
				// Date d1 = dateOfJoining.getTime();
				end.set(end.get(Calendar.YEAR), 11, 30);
				System.out.println("end is " + end.getTime());
				System.out.println("dateOfJoining is" + dateOfJoining.getTime());
				difInMonths = end.get(Calendar.MONTH) - dateOfJoining.get(Calendar.MONTH);

				System.out.println("difInMonths " + difInMonths);

			} else {
				// generate for full year
				difInMonths = -1;

			}

		} else {
			// its APL-MAR
		}

		UserLeaves userLeaves = new UserLeaves();
		int i = 1;
		System.out.println("1");

		userLeaves.setCompanyId(companyId);
		userLeaves.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		userLeaves.setCurrent(true);
		userLeaves.setId(UUID.randomUUID().toString());
		userLeaves.setUserId(userId);
		userLeaves.setYear(year);

		List<LeaveDetails> details = new ArrayList<>();

		for (LeaveTypes lt : leaveTypes) {
			LeaveDetails l = new LeaveDetails();
			l.setIndex(i++);

			if (lt.isCarryFarward()) {
				if (lastYearLeaves != null) {
					// yes we got last year data
					int carrayFowardlimit = lt.getCarryFarwardLimit();
					LeaveDetails oldDetails = getLeaveType(lastYearLeaves, lt.getName());
					double forwardLeaves = oldDetails.getCount() - oldDetails.getTakenLeaves();
					if (forwardLeaves > carrayFowardlimit) {
						forwardLeaves = carrayFowardlimit;
					}
					l.setCount(lt.getCount() + forwardLeaves);
					l.setHiddenCarrayFowardCount(forwardLeaves);
				} else {
					if (lt.getAssingType().equals("Full")) {
						l.setCount(lt.getCount());
						l.setAccumulatedLeaves(l.getCount());
					} else {
						// calculate the accumulated leaves

						if (difInMonths == -1) {
							l.setCount(lt.getCount());
						} else {

							double va = (double) (lt.getPerMonth() * difInMonths);
							l.setCount(getLeaves(va));
							if (isFirstTime) {
								if (dateOfJoining.get(Calendar.MONTH) == 12) {
									l.setAccumulatedLeaves(l.getCount());
								} else {
									l.setAccumulatedMonth(dateOfJoining.get(Calendar.MONTH));
									l.setAccumulatedLeaves(lt.getPerMonth());
								}

							} else {
								l.setAccumulatedLeaves(l.getCount());
							}
						}
					}
				}
			} else {

				if (lt.getAssingType() == "Full") {
					l.setCount(lt.getCount());
					l.setAccumulatedLeaves(l.getCount());
				} else {
					// calculate the accumulated leaves

					if (difInMonths == -1) {
						l.setCount(lt.getCount());
					} else {

						double va = (double) (lt.getPerMonth() * difInMonths);
						l.setCount(getLeaves(va));
						if (isFirstTime) {
							if (dateOfJoining.get(Calendar.MONTH) == 12) {
								l.setAccumulatedLeaves(l.getCount());
							} else {
								l.setAccumulatedMonth(dateOfJoining.get(Calendar.MONTH));
								l.setAccumulatedLeaves(lt.getPerMonth());
							}

						} else {
							l.setAccumulatedLeaves(l.getCount());
						}

					}

				}

			}

			l.setLeaveName(lt.getName());
			l.setTakenLeaves(0);
			details.add(l);
			//
			//
			// switch (lt.getStartType()) {
			// case "Year":
			// //get till date month
			// if(lt.getAccType().equals("Accumulated")){
			// int month=cal.get(Calendar.MONTH);
			// float perMonth =(float)( lt.getCount())/(float)12;
			// System.out.println("month is "+month);
			// Double allowed = Math.ceil(((float)((12-month)*perMonth)));
			// System.out.println(" ok month allwoed is " + allowed);
			// }
			//
			// break;
			//
			// default:
			// break;
			// }
		}
		userLeaves.setLeaves(details);
		try{
		repo.save(userLeaves);
		} catch (Exception ex) {
			throw new UnableToReadException("leave-006", "Error while saving/updating user leave details", referenceId, ex);
		}
		return null;
	}

	LeaveDetails getLeaveType(UserLeaves userLeave, String leaveName) {
		LeaveDetails details = null;
		if (userLeave != null && userLeave.getLeaves() != null) {
			for (LeaveDetails det : userLeave.getLeaves()) {
				if (det.getLeaveName().equalsIgnoreCase(leaveName)) {
					details = det;
					break;
				}
			}
		}

		return details;
	}

	public int getMonthNumber(String month) {
		if (month == null || "".equals(month.trim())) {
			return 0;
		}
		switch (month) {
		case "january":
			return 0;
		case "february":
			return 1;
		case "march":
			return 2;
		case "april":
			return 3;
		case "may":
			return 4;
		case "june":
			return 5;
		case "july":
			return 6;
		case "august":
			return 7;
		case "september":
			return 8;
		case "october":
			return 9;
		case "november":
			return 10;
		case "december":
			return 11;
		default:
			return 0;
		}
	}

	@Override
	public UserLeaves getByUserIdAndYear(String userId, int year)throws AppException {
		String referenceId = Utils.getReferenceId("LEAVES");
		try {
		return repo.findByUserIdAndYear(userId, year);
		} catch (Exception ex) {
			throw new UnableToReadException("leave-007", "Error while fetching user leave details for user id " + userId + " year " + year, referenceId, ex);
		}
	}

	@Override
	public UserLeaves getByUserIdAndFinancialYear(String userId, String year) throws AppException {
		String referenceId = Utils.getReferenceId("LEAVES");
		try {
		return repo.findByUserIdAndFinancialYear(userId, year);
		} catch (Exception ex) {
			throw new UnableToReadException("leave-008", "Error while fetching user leave details for user id " + userId + " financial year " + year, referenceId, ex);
		}
	}

}
