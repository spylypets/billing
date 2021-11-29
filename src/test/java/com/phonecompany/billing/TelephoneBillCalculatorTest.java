package com.phonecompany.billing;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import org.junit.Test;

public class TelephoneBillCalculatorTest {
	
	TelephoneBillCalculator billCalculator = new TelephoneBillCalculatorImpl();
	
	@Test
	public void testShortCall() {
		
		BigDecimal result = billCalculator.calculate("420774577453,13-01-2020 8:10:15,13-01-2020 8:10:57");
		assertTrue(result.doubleValue() == 1.0);
	}
	
	@Test
	public void testBaseRateCall() {
		
		BigDecimal result = billCalculator.calculate("420774577453,13-01-2020 8:10:15,13-01-2020 8:13:57");
		assertTrue(result.doubleValue() == 3.0);
	}
	
	@Test
	public void testDiscountRateCall() {
		
		BigDecimal result = billCalculator.calculate("420774577453,13-01-2020 17:10:15,13-01-2020 17:13:57");
		assertTrue(result.doubleValue() == 1.5);
	}
	
	@Test
	public void testCombinedRateCall() {
		
		BigDecimal result = billCalculator.calculate("420774577453,13-01-2020 7:58:15,13-01-2020 8:02:57");
		assertTrue(result.doubleValue() == 3.0);
	}
	
	@Test
	public void testBonusDurationCall() {
		
		BigDecimal result = billCalculator.calculate("420774577453,13-01-2020 8:10:15,13-01-2020 8:20:57");
		assertTrue(result.doubleValue() == 6.0);
	}
	
	@Test
	public void testFavoriteNumber() {
		
		BigDecimal result = billCalculator.calculate("420123456789,13-01-2020 8:10:15,13-01-2020 8:10:57\n420333456789,13-01-2020 8:10:15,13-01-2020 8:10:57\n420333456789,13-01-2020 8:10:15,13-01-2020 8:10:57\n420777456789,13-01-2020 8:10:15,13-01-2020 8:10:57\n420777456789,13-01-2020 8:10:15,13-01-2020 8:10:57\n420777456789,13-01-2020 8:10:15,13-01-2020 8:10:57\n");
		assertTrue(result.doubleValue() == 3.0);
	}

}
