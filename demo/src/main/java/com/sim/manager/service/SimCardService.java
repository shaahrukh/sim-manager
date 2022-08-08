package com.sim.manager.service;

import com.sim.manager.model.SimCardDetails;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public interface SimCardService {
	
	public void addSim(SimCardDetails details);
	
	public List<SimCardDetails> getAllSim();
	
	public void updateSim(SimCardDetails details, int id);
	
	public void deleteSim(int id);
	
	public List<SimCardDetails> toBeRenewed();
	
	public void renewSim(Integer id);

}
