package es.ruben.spring.tallerchile.service;

import org.junit.Test;

import es.ruben.spring.tallerchile.service.WeatherForecastService;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class WeatherForecastServiceTest {
	
	private String[][] forecastData = new String[][] {
			{"22.2S", "82", "Rain", "Altas presiones implican mayor probabilidad de lluvia"},
			{"22.2S", "79", "No forecast", "En estas condiciones el pronóstico es incierto"},
			{"27s", "70", "Sunny", "Bajas presiones y humedad alta dismuye la probabilidad de lluvia"},
			{"18s", "84", "No forecast", "Presiones demasiado bajas hacen que el pronóstico sea incierto"},
			{"27s", "80", "Sunny", "Alta probabilidad de tiempo soleado"},
			{"27s", "81", "Rain", "Alta probabilidad de lluvia cuando la humedad supera 80"}
	};

	@Test
	public void forecast_matches_expected_result() {
		
		WeatherForecastService service = new WeatherForecastService();
		
		for(int i=0; i<forecastData.length; i++) {
			
			// given
			String pressure = forecastData[i][0];
			String humidity = forecastData[i][1];
			
			// when
			String forecast = service.forecastWeather(pressure, humidity);
			
			// then
			String expectedForecast = forecastData[i][2];
			String reason = "Pronóstico para presión:" + pressure + " y humedad:" + humidity +
					" debería ser " + expectedForecast + " (" + forecastData[i][3] + ")";
			
			assertThat(reason, forecast, is(expectedForecast));
			
		}
	}

}
