package com.phonecompany.billing.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Call {
	
	private Long number;
	
	private Date start;
	
	private Date end;

}
