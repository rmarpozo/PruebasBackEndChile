package es.ruben.spring.tallerchile.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdyne.ws.weatherws.WeatherReturn;
import com.cdyne.ws.weatherws.WeatherSoap;

import es.ruben.spring.tallerchile.dao.HistoricDataDAO;
import es.ruben.spring.tallerchile.service.model.HistoricData;
import es.ruben.spring.tallerchile.service.model.WeatherInfo;

@Service
public class WeatherService {
	
	@Autowired
	WeatherSoap weatherPort;
	
	@Autowired
	WeatherForecastService forecastService;
	
	@Autowired 
	HistoricDataDAO historicDataDAO;
	
	public WeatherInfo getCityWeatherByZip(String zipCode) {
		
		WeatherReturn result = weatherPort.getCityWeatherByZIP(zipCode);
		
		WeatherInfo winfo = new WeatherInfo();
		winfo.setCity(result.getCity());
		winfo.setForecast(forecastService.forecastWeather(result.getPressure(), result.getRelativeHumidity()));
				
		return winfo;
	}
	
	public void saveForecast(WeatherInfo weatherInfo) {
		
		HistoricData data = new HistoricData();
		String forecast = weatherInfo.getForecast();
		
		data.setCity(weatherInfo.getCity());
		data.setForecast(forecast);
		data.setForecastDate(new Date());
		
		if(!forecast.equals("No forecast")) {
			historicDataDAO.save(data);
		}
		
	}
	
}
