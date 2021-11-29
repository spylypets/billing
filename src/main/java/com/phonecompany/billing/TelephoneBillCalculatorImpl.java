package com.phonecompany.billing;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.phonecompany.billing.model.Call;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator {

	@Override
	public BigDecimal calculate(String phoneLog) {
		
		double result = 0;
		final Map<Long, Integer> numberOfCalls = new HashMap<>();
		final List<Call> calls = new ArrayList<Call>();
		phoneLog.lines().forEach(l -> {
			
			String[] callData = l.split(",");
			numberOfCalls.computeIfPresent(Long.valueOf(callData[0]), (key, val) -> val + 1);
			numberOfCalls.putIfAbsent(Long.valueOf(callData[0]), 1);
			try {
				calls.add(new Call(Long.valueOf(callData[0]), DATE_FORMAT.parse(callData[1]), DATE_FORMAT.parse(callData[2])));
			} catch (ParseException e) {
				log.error("Error while parsing a date value.", e);
			}
		});
		Long favoriteNumber = getFavoriteNumber(numberOfCalls);
		for (Call c : calls) {
			
			if(c.getNumber().equals(favoriteNumber)) continue;
			
			result = result + calculateCallPrice(c.getStart(), c.getEnd());
		}
		return new BigDecimal(result);
	}
	
	private Long getFavoriteNumber(final Map<Long, Integer> numberOfCalls) {
		
		Entry<Long, Integer> favoriteNumberEntry = numberOfCalls.entrySet().stream().sorted(Comparator.comparing(Entry::getValue, Comparator.reverseOrder())).findFirst().get();
		return favoriteNumberEntry.getValue() > 1 ? favoriteNumberEntry.getKey() : null;
	}
	
	private double calculateCallPrice(Date start, Date end) {
		
		double basePrice = 0, bonusDurationPrice = 0;
		long callDuration = (end.getTime() - start.getTime())/60000;
		long baseCallDuration = callDuration;
		if (callDuration > BONUS_DURATION) {
			bonusDurationPrice = (callDuration - BONUS_DURATION) * BONUS_DURATION_RATE;
			baseCallDuration = BONUS_DURATION;
		}
		
		LocalTime startTime = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault()).toLocalTime();
		LocalTime endTime = startTime.plusMinutes(baseCallDuration);
		//Calculate the price for each started minute in particular
		do {
			basePrice = basePrice + getRate(startTime);
			startTime = startTime.plusMinutes(1);
		} while (startTime.isBefore(endTime));
		return basePrice + bonusDurationPrice;
	}
	
	private double getRate(LocalTime callTime) {
		
		if(callTime.isAfter(DISCOUNT_START_TIME) || callTime.isBefore(DISCOUNT_END_TIME)) {
			return DISCOUNT_TIME_RATE;
		} else {
			return COMMON_RATE;
		}
	}

}
