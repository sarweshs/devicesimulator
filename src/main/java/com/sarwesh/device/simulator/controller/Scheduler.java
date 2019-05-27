package com.sarwesh.device.simulator.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ixortalk.iot.client.core.IotClient;
import com.sarwesh.device.simulator.model.SensorModel;

@Component
public class Scheduler {
	@Inject
	private IotClient iotClient;

	@Autowired
	SensorModel model;

	@Scheduled(fixedRateString = "${scheduler.fixedrate}")
	public void fixedRateSch() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		DecimalFormat df = new DecimalFormat("##.00");
		Date now = new Date();
		String strDate = sdf.format(now);
		System.out.println("Fixed Rate scheduler:: " + strDate);

		Double min = 15.0; // Set To Your Desired Min Value
		Double max = 50.0; // Set To Your Desired Max Value
		
		double randomHumidity = (Math.random() * ((max - min) + 1)) + min;
		model.setHumidity((double)Math.round(randomHumidity * 100)/100);

		double randomTemp = (Math.random() * ((35 - 10) + 1)) + min;

		model.setTemperature((double)Math.round(randomTemp * 100)/100);
		model.setTimestamp(strDate);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writeValueAsString(model);
			System.out.println(jsonString);
			iotClient.publish(jsonString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
