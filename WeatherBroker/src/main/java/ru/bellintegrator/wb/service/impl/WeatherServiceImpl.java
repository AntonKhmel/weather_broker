package ru.bellintegrator.wb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.bellintegrator.wb.dao.WeatherDAO;
import ru.bellintegrator.wb.dao.impl.WeatherDAOImpl;
import ru.bellintegrator.wb.exception.DataAccessError;
import ru.bellintegrator.wb.model.Weather;
import ru.bellintegrator.wb.model.view.WeatherView;
import ru.bellintegrator.wb.service.WeatherService;
import ru.bellintegrator.wb.service.json.Convert;

import javax.jms.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author Хмель А.В.
 * class Service for object Weather by work with business logic
 */
@Service
public class WeatherServiceImpl implements WeatherService {
    private final Logger LOG = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Autowired
    WeatherDAO weatherDAO;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Convert convert;

    @Autowired
    JmsTemplate myJmsTemplate;

    // get json the weather by city
    @Override
    public void getWeatherYahoo(String city) throws DataAccessError {
        LOG.info("get json the weather by city");

        Weather weather = null;

        String json = restTemplate.getForObject("https://query.yahooapis.com/v1/public/" +
                "yql?q=select wind from weather.forecast where woeid in (select woeid from geo.places(1) where " +
                "text='" + city + "')&format=json", String.class);

        if (city != null && !city.trim().isEmpty()) {
            weather = convert.jsonToWeather(json, city);
        }

        if (weather != null) {
            sendMessage(weather);
        }
    }

    // call the DAO object method for get the object Weather by city
    @Override
    @Transactional(readOnly = true)
    public WeatherView getWeather(String city) throws DataAccessError {
        LOG.info("call method DAO for get the object Weather by city");

        WeatherView weatherView = null;

        Weather weather = weatherDAO.getWeather(city);

        if (weather != null) {
            weatherView = convert.weatherToJson(weather);
        }

        return weatherView;
    }

//    @Override
//    @Transactional()
//    public void saveWeather(Weather weather) throws DataAccessError {
//        LOG.info("call method DAO for save the object Weather");
//
//        weatherDAO.saveWeather(weather);
//    }

    // send the object Weather in jms queue
    @Override
    @Transactional()
    public void sendMessage(final Weather weather) throws DataAccessError {
        LOG.info("send the object Weather in jms queue");

        myJmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage(weather);
                return objectMessage;
            }
        });
    }
}
