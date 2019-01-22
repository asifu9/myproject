package com.share.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Utils {

	
	public static String convertDate(Date dt){
		StringBuilder result=new StringBuilder();
		Calendar cal=Calendar.getInstance();
		result.append(new TimeDifference(cal.getTime(),dt ).getDifferenceString());
		return result.toString();
	}
	
	public static String getReferenceId(String type){
		return type+"-"+UUID.randomUUID().toString();
	}
	
	public static String getMonth(int number){
		switch (number+1) {
		case 1:
			return "jan";
		case 2:
			return "feb";
case 3:
			return "mar";
		case 4:
			return "apr";
case 5:
	return "may";
		case 6:
			return "june";
case 7:
	return "jul";
		case 8:
			return "aug";
case 9:
	return "sep";
		case 10:
			return "oct";
case 11:
	return "nov";
		case 12:
			return "dec";
			
		default:
			break;
		}
		return "";
	}
}
