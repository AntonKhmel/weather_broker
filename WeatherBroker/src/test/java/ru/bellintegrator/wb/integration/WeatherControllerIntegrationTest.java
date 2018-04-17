package ru.bellintegrator.wb.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.bellintegrator.wb.dao.WeatherDAO;
import ru.bellintegrator.wb.exception.DataAccessError;
import ru.bellintegrator.wb.model.Weather;
import ru.bellintegrator.wb.model.view.WeatherView;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:testApplicationContext.xml"})
public class WeatherControllerIntegrationTest {
    private final Logger LOG = LoggerFactory.getLogger(WeatherControllerIntegrationTest.class);

    private static final String BASE_URI = "http://localhost:8080/WeatherBroker_war_exploded/weather";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherDAO weatherDAO;

    @Before
    public void addCity(){
        WeatherView weatherView = new WeatherView("chicago", "");
        try {
            HttpStatus hs = restTemplate.postForEntity(BASE_URI + "/yahoo", weatherView, HttpStatus.class).getStatusCode();
            assertThat(hs, is(HttpStatus.OK));
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    public void getWeatherYahoo(){
        WeatherView weatherView = new WeatherView("chicago", "");
        try {
            HttpStatus hs = restTemplate.postForEntity(BASE_URI + "/yahoo", weatherView, HttpStatus.class).getStatusCode();
            assertThat(hs, is(HttpStatus.OK));
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    public void getWeatherDb(){
        WeatherView reqWeather = new WeatherView("chicago", "");
        try {
            WeatherView weatherView = restTemplate.postForObject(BASE_URI + "/db", reqWeather, WeatherView.class);
            assertNotNull(weatherView);
            assertThat(weatherView.getCity(), is("chicago"));
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.FORBIDDEN));
        }
    }
}
