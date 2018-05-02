package com.example.demo;

import com.example.demo.entities.Data;
import com.example.demo.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	@Autowired
	DataService dataService;

	@Autowired
	PiService piService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	final int time =  1000 * 60 * 5;
	@Scheduled(fixedRate = time)
	private void writeTempToDB(){
		Data data = new Data();
		data.setTemperature(piService.getTemperature());
		this.dataService.save(data);
	}
}
