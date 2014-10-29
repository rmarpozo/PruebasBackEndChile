package es.ruben.spring.tallerchile.it;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:spring/taller-chile-context.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,DbUnitTestExecutionListener.class})
public class WeatherControllerIT {
	
	private ClientAndServer mockServer;
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Before
	public void startMockServer() {
		mockServer = ClientAndServer.startClientAndServer(8084);
	}
	
	@After
	public void stopMockServer() {
		mockServer.reset();
		mockServer.stop();
	}
	
	@Test
	@DatabaseSetup("controller_it_database.xml")
	@ExpectedDatabase(
			value="expected_result_controller_it.xml",
			table="HistoricData",
			query="Select forecast, city from HistoricData",
			assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void successful_request_with_new_york_zip_code() throws Exception {
		
		//given that there is a forecast web service
		mockServer
		.when(
                request()
                        .withMethod("POST")
                        .withPath("/")
        )
        .respond(
                response()
                        .withStatusCode(200)
                        .withHeaders(
                                new Header("Content-Type", "text/xml; charset=utf-8")
                        )
                        .withBody(loadResponse("response1"))
        );
		
		//when the application is called with the new york zip code
		mockMvc.perform(get("/10001").accept(MediaType.APPLICATION_JSON)) 
		
		//then we receive the forcast in json format
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.forecast").value("Rain"));
		
	}
	
	private byte[] loadResponse(String responseName) throws IOException {
		
		InputStream in = getClass().getClassLoader().getResourceAsStream(responseName);
		
		if(in == null) throw new IllegalArgumentException("Response doesn't exist");
		
		return IOUtils.toByteArray(in);
	}

}
