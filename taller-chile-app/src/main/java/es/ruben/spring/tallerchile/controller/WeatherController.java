package es.ruben.spring.tallerchile.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.ruben.spring.tallerchile.service.WeatherService;
import es.ruben.spring.tallerchile.service.model.WeatherInfo;

@Controller
public class WeatherController {
	
	@Autowired
	WeatherService weatherService;

	@RequestMapping(value="/{zipCode}")
	public ResponseEntity<WeatherInfo> test(HttpServletResponse response,
			@PathVariable String zipCode) throws IOException{
				
		WeatherInfo info = weatherService.getCityWeatherByZip(zipCode);
		
		weatherService.saveForecast(info);
		
		return new ResponseEntity<WeatherInfo>(info, HttpStatus.OK);
	}
}
