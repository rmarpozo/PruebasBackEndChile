package es.ruben.spring.tallerchile.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import es.ruben.spring.tallerchile.dao.HistoricDataDAO;
import es.ruben.spring.tallerchile.service.model.HistoricData;
import es.ruben.spring.tallerchile.service.model.WeatherInfo;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {
	
	@Mock
	HistoricDataDAO historicDataDao;
	
	@InjectMocks
	WeatherService weatherService;
	
	@Test
	public void save_data_if_forecast_ok() {
		WeatherInfo info = new WeatherInfo();
		info.setCity("New York");
		info.setForecast("Rain");
		
		weatherService.saveForecast(info);
		
		ArgumentCaptor<HistoricData> historicData = ArgumentCaptor.forClass(HistoricData.class);
		verify(historicDataDao).save(historicData.capture());
		assertThat(historicData.getValue().getCity(), is("New York"));
		assertThat(historicData.getValue().getForecast(), is("Rain"));
	}
	
	@Test
	public void not_save_data_if_forecast_is_ko() {
		WeatherInfo info = new WeatherInfo();
		info.setCity("New York");
		info.setForecast("No forecast");
		
		weatherService.saveForecast(info);
		
		verify(historicDataDao, never()).save(any(HistoricData.class));
		
	}

}
