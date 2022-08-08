package com.sim.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sim.manager.model.SimCardDetails;
import com.sim.manager.service.SimCardService;

@RestController
public class SimCardRest {

	@Autowired
	SimCardService cardService;

	@GetMapping("/")
	public void statusCheck() {

	}

	@PostMapping("/add")
	public String addSim(@RequestBody SimCardDetails details) {
		cardService.addSim(details);
		return ("Sim Details Saved");
	}

	@DeleteMapping("/")
	public String deleteSimByID(@RequestParam int id) {
		cardService.deleteSim(id);
		return "Sim Deleted Successfully";
	}

	@PostMapping("/")
	public String updateSimById(@RequestParam int id, @RequestBody SimCardDetails details) {
		cardService.updateSim(details, id);
		return "Sim Updated Successfully";
	}

	@GetMapping("/listall")
	public List<SimCardDetails> getAllSim() {
		return cardService.getAllSim();
	}

	@GetMapping("/to-renew")
	public List<SimCardDetails> getSimToBeRenewd() {
		return cardService.toBeRenewed();
	}

	@PostMapping("/renew")
	public String renewSimById(@RequestParam Integer id) {
		cardService.renewSim(id);
		return "Sim is renewd";
	}

}
