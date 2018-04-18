package ru.bellintegrator.wb.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.bellintegrator.wb.controller.WeatherController;
import ru.bellintegrator.wb.model.Weather;
import ru.bellintegrator.wb.model.view.WeatherView;
import ru.bellintegrator.wb.service.WeatherService;
import ru.bellintegrator.wb.service.json.Convert;

import javax.jms.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:testApplicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class WeatherControllerUnitTest {
    private MockMvc mockMvc;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @Autowired
    private JmsTemplate myJmsTemplate;

    @Autowired
    private Convert convert;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(weatherController)
                .build();
    }

    @Test
    public void getWeatherDb() throws Exception {
        WeatherView reqWeather = new WeatherView("chicago", "");
        WeatherView weatherView = new WeatherView("chicago", "{query{count: 1,created: 2018-03-13T09:45:45Z,lang: ru-RU,results: {channel: " +
                "{wind: {chill: 19,direction: 320,speed: 18}}}}}");

        when(weatherService.getWeather(reqWeather.getCity())).thenReturn(weatherView);

        mockMvc.perform(post("/weather/db")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(reqWeather)))
                .andExpect(status().isOk());

        verify(weatherService, times(1)).getWeather(reqWeather.getCity());
        verifyNoMoreInteractions(weatherService);
    }

    @Test
    public void getWeatherYahoo() throws Exception {
        WeatherView weather = new WeatherView("chicago", "");

        doNothing().when(weatherService).getWeatherYahoo(weather.getCity());

        mockMvc.perform(
                post("/weather/yahoo")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(asJsonString(weather)))
                .andExpect(status().isOk());

        verify(weatherService, times(1)).getWeatherYahoo(weather.getCity());
        verifyNoMoreInteractions(weatherService);
    }

    @Test
    public void sendMessage() {
        Weather weather = new Weather("chicago", 1, "2018-03-19T12:14:32Z", "RU-ru", 25, 12, 32);

        myJmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage(weather);
                return objectMessage;
            }
        });
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
