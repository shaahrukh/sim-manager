package com.sim.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sim.manager.model.SimCardDetails;

@Repository
public interface SimCardRepository
		extends JpaRepository<SimCardDetails, Integer> {

}
