package es.ruben.spring.tallerchile.service.model;

public class WeatherInfo {
	
	private String city;
	private String forecast;
	
	public String getCity() {
		return city;
	}
	
	public String getForecast() {
		return forecast;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
}
