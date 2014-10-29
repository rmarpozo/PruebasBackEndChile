package tallerchile.clientews.it;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cdyne.ws.weatherws.WeatherReturn;
import com.cdyne.ws.weatherws.WeatherSoap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ws-proxy-context.xml")
public class WeatherIT {
	
	private ClientAndServer mockServer;
	
	@Resource 
	WeatherSoap weatherPort;
	
	@Before
	public void startMockServer() {
		mockServer = ClientAndServer.startClientAndServer(8084);
	}
	
	@After
	public void stopMockServer() {
		mockServer.stop();
	}
	
	@Test
	public void check_pressure_for_new_york() throws IOException {
		
		//given there is a forecast web service
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

		//when we call the webservice with New York's zipcode
		WeatherReturn weatherResult = weatherPort.getCityWeatherByZIP("10001");
		
		//then we get New York's weather
		assertThat(weatherResult.getPressure(), is("29.97S"));
		assertThat(weatherResult.getResponseText(),containsString("TEST"));
	}
	
	private byte[] loadResponse(String responseName) throws IOException {
		
		InputStream in = getClass().getClassLoader().getResourceAsStream(responseName);
		
		if(in == null) throw new IllegalArgumentException("Response doesn't exist");
		
		return IOUtils.toByteArray(in);
	}
	
}
