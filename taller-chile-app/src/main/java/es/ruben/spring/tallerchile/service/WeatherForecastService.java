package es.ruben.spring.tallerchile.service;

import org.springframework.stereotype.Service;

@Service
public class WeatherForecastService {
	
	public String forecastWeather(String pressureStr, String relativeHumidityStr) {
		
		double pressure = Double.parseDouble(pressureStr.substring(0, pressureStr.length()-1));
		int relativeHumidity = Integer.parseInt(relativeHumidityStr);
		
		String forecast = "No forecast";
		
		if(pressure > 25 && relativeHumidity <= 80) {
			forecast = "Sunny";
		}
		else if(pressure > 20 && relativeHumidity > 80) {
			forecast = "Rain";
		}
		
		return forecast;
	}

}
