package com.sim.manager.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimCardDetails {
	
	@Id
	@GeneratedValue
	private int id;
	
	private long simCardNo;
	
	private long mobileNumber;
	
	private String status;
	
	private Timestamp expiryDate;
	
	private String stateOfRegistration;
	
	private String kyc;
	
	private String telecomProvider;
	
	private String fullName;

}
