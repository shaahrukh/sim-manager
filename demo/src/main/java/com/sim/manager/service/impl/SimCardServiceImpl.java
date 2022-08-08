package com.sim.manager.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.manager.model.SimCardDetails;
import com.sim.manager.repository.SimCardRepository;
import com.sim.manager.service.SimCardService;

@Service
public class SimCardServiceImpl implements SimCardService{
	
	private static final String POST_URL = "http://localhost:8080/verification/verify/subscription";

	@Autowired
	SimCardRepository cardRepository;
	
	@Override
	public void addSim(SimCardDetails details) {
		cardRepository.save(details);
	}

	@Override
	public List<SimCardDetails> getAllSim() {
		return cardRepository.findAll();
	}

	@Override
	public void updateSim(SimCardDetails cardDetails, int id) {
		SimCardDetails details = cardRepository.getById(id);
		details.setExpiryDate(cardDetails.getExpiryDate());
		details.setFullName(cardDetails.getFullName());
		details.setKyc(cardDetails.getKyc());
		details.setMobileNumber(cardDetails.getMobileNumber());
		details.setSimCardNo(cardDetails.getSimCardNo());
		details.setStateOfRegistration(cardDetails.getStateOfRegistration());
		details.setStatus(cardDetails.getStatus());
		details.setTelecomProvider(cardDetails.getTelecomProvider());
		cardRepository.save(details);
	}

	@Override
	public void deleteSim(int id) {
		cardRepository.deleteById(id);
	}

	@Override
	public List<SimCardDetails> toBeRenewed() {
		List<SimCardDetails> allSim= cardRepository.findAll();
		List<SimCardDetails> toBeRenewed = new ArrayList<>();
		Timestamp startDate;
		startDate = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(startDate.getTime());
		cal.add(Calendar.DAY_OF_MONTH, + 30);
		startDate = new Timestamp(cal.getTime().getTime());
		for(SimCardDetails sCD : allSim) {
			if(sCD.getExpiryDate().before(startDate)) {
				toBeRenewed.add(sCD);
			}
		}
		return toBeRenewed;
	}

	@Override
	public void renewSim(Integer id) {
		SimCardDetails details = cardRepository.getById(id);
		Timestamp oldExpiry = details.getExpiryDate();
		Timestamp newExpiry;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(oldExpiry.getTime());
		cal.add(Calendar.DAY_OF_MONTH, + 30);
		newExpiry =  new Timestamp(cal.getTime().getTime());
		details.setExpiryDate(newExpiry);
		cardRepository.save(details);
		try {
			sendPost(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String sendPost(Integer id) throws IOException {
		URL url = new URL(POST_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();
		os.write(id);
		os.flush();
		os.close();
		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} else {
			return "POST request not worked";
		}
	}
}
