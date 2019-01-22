package com.share.api;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeCal {

	public static long getCurrentDateTimeSeconds(){
		return ZonedDateTime.now(ZoneId.of("UTC")).toInstant().getEpochSecond();
	}
}
