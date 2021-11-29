package com.phonecompany.billing;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

public interface TelephoneBillCalculator {
	
	static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	static final short BONUS_DURATION = 5;
	
	static final LocalTime DISCOUNT_START_TIME = LocalTime.of(16, 0);
			
	static final LocalTime DISCOUNT_END_TIME = LocalTime.of(8, 0);
	
	static final double COMMON_RATE = 1.0;
	
	static final double DISCOUNT_TIME_RATE = 0.5;
	
	static final double BONUS_DURATION_RATE = 0.2;
	
	BigDecimal calculate (String phoneLog);

}
