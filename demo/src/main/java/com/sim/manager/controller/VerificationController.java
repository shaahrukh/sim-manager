package com.sim.manager.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sim.manager.model.SimCardDetails;
import com.sim.manager.repository.SimCardRepository;

@RestController
@RequestMapping("/verification")
public class VerificationController {
	
	@Autowired
	SimCardRepository cardRepository;
	
	@PostMapping("/verify/subscription")
	public String verifySub(@RequestParam Integer id) {
		SimCardDetails details = cardRepository.getById(id);
		Timestamp tDate = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(tDate.getTime());
		cal.add(Calendar.DAY_OF_MONTH, + 30);
		tDate = new Timestamp(cal.getTime().getTime());
		return (details.getExpiryDate().after(tDate)) ? "Subscription Update" : "Subscription is not updated";
	}
	

}
