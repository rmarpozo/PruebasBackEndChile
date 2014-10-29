package es.ruben.spring.tallerchile.service.model;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class HistoricDataTest {
	
	@Test
	public void random_id_is_created_when_using_constructor() {
		
		// given
		HistoricData data = null;
		HistoricData data2 = null;
		
		// when
		data = new HistoricData();
		data2 = new HistoricData();
		
		// then
		String id = data.getId();
		String id2 = data2.getId();
		
		assertNotNull("El id no puede ser nulo", id);
		assertNotNull("El id no puede ser nulo", id2);
		
		assertThat("Los ids deben ser únicos cuando se generan con el constructor", id, not(is(id2)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void id_cannot_be_longer_than_50_characters() {
		
		// given
		HistoricData data = new HistoricData();
		
		// when
		data.setId("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		// then IllegalArgumenException thrown. 
		
	}
	
	@Test
	public void id_length_must_be_less_than_50_characters() {
		
		// --- Example 1
		// given
		HistoricData data = new HistoricData();
		String dataTest = "12345678901234567890123456789012345678901234567890";
		
		// when
		data.setId(dataTest);
		
		// then
		assertThat(data.getId(), is(dataTest));
		
		// --- Example 2
		// given
		data = new HistoricData();
		dataTest = "1234567890";
		
		// when
		data.setId(dataTest);
		
		// then
		assertThat(data.getId(), is(dataTest));
		
		
	}

}
