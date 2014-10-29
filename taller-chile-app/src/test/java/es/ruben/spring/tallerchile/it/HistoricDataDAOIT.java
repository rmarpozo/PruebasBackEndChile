package es.ruben.spring.tallerchile.it;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import es.ruben.spring.tallerchile.dao.HistoricDataDAO;
import es.ruben.spring.tallerchile.service.model.HistoricData;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/dao-context.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,DbUnitTestExecutionListener.class})
public class HistoricDataDAOIT {
	
	@Autowired
	private HistoricDataDAO historicDataDAO;
	
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	@Test
	@DatabaseSetup("database.xml")
	@ExpectedDatabase(value="expectedResult.xml",assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void save_historicData_to_database() throws Exception {
		
		// given that database is created
		
		// when
		HistoricData data = new HistoricData();
		data.setForecast("Rain");
		data.setId("testId");
		data.setCity("Santiago");
		
		data.setForecastDate(format.parse("01/01/2014"));
		
		historicDataDAO.save(data);
		
		// then database state must be expectedResult.xml
	}
	
	@Test
	@DatabaseSetup("database.xml")
	public void get_historic_data_by_id() throws Exception {
		
		// given that database has a record with id "id1"
		
		// when
		HistoricData result = historicDataDAO.getById("id1");
		
		// then
		assertThat(result.getId(), is("id1"));
		assertThat(result.getForecast(), is("Rain"));
		assertThat(result.getForecastDate(), is(format.parse("01/01/2014")));
		assertThat(result.getCity(), is("Madrid"));
	}
	
	@Test
	@DatabaseSetup("database.xml")
	public void null_when_id_does_not_exist() throws Exception {
		
		// given that database doesn't have any record with id "no_exist"
		
		// when
		HistoricData result = historicDataDAO.getById("no_exist");
		
		// then
		assertThat(result, nullValue());
	}
	
	@Test
	@DatabaseSetup("database.xml")
	public void get_historic_data_by_date() throws Exception {
		
		// given that the database has a record with date 1/1/2014
		
		// when
		Date testDate = format.parse("01/01/2014");
		List<HistoricData> result = historicDataDAO.getByDate(testDate);
		
		// then
		assertThat("La lista debe contener un único resultado", result.size(), is(1));
		HistoricData data = result.get(0);
		
		assertThat(data.getId(), is("id1"));
		assertThat(data.getForecast(), is("Rain"));
		assertThat(data.getForecastDate(), is(testDate));
		assertThat(data.getCity(), is("Madrid"));
	}
	
	@Test
	@DatabaseSetup("database.xml")
	public void historic_data_by_date_does_not_exist() throws Exception {
		
		// given that the database doesn't have a record with date 2/1/2014
		
		// when
		Date testDate = format.parse("02/01/2014");
		List<HistoricData> result = historicDataDAO.getByDate(testDate);
		
		// then
		assertThat(result, notNullValue());
		assertThat(result.size(), is(0));
	}
	
	@Test
	@DatabaseSetup("database_multiple_records_same_date.xml")
	public void get_multiple_historic_data_by_date() throws Exception {
		
		// given that the database has some records with date 1/1/2014
		
		// when
		Date testDate = format.parse("01/01/2014");
		List<HistoricData> result = historicDataDAO.getByDate(testDate);
		
		// then
		assertThat("La lista debe contener dos resultados", result.size(), is(2));
		HistoricData data = result.get(0);
		
		assertThat(data.getId(), is("id1"));
		assertThat(data.getForecast(), is("Rain"));
		assertThat(data.getForecastDate(), is(testDate));
		assertThat(data.getCity(), is("Madrid"));
		
		data = result.get(1);
		
		assertThat(data.getId(), is("id3"));
		assertThat(data.getForecast(), is("Sunny"));
		assertThat(data.getForecastDate(), is(testDate));	
		assertThat(data.getCity(), is("New York"));
	}

}
