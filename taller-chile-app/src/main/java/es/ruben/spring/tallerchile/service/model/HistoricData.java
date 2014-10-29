package es.ruben.spring.tallerchile.service.model;

import java.util.Date;
import java.util.UUID;

public class HistoricData {
	
	private Date forecastDate;
	private String forecast;
	private String id;
	private String city;
	
	public HistoricData() {
		id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		if(id.length() > 50) {
			throw new IllegalArgumentException("El tamaño del id no puede superar los 50 caracteres");
		}
		
		this.id = id;
	}
	
	public Date getForecastDate() {
		return forecastDate;
	}
	
	public String getForecast() {
		return forecast;
	}
	
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
	
	public void setForecastDate(Date forecastDate) {
		this.forecastDate = forecastDate;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

}
