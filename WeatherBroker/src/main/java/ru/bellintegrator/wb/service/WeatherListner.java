package ru.bellintegrator.wb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.wb.dao.WeatherDAO;
import ru.bellintegrator.wb.dao.impl.WeatherDAOImpl;
import ru.bellintegrator.wb.exception.DataAccessError;
import ru.bellintegrator.wb.model.Weather;

import java.io.IOException;

@Component
@EnableTransactionManagement
public class WeatherListner {
    private final Logger LOG = LoggerFactory.getLogger(WeatherDAOImpl.class);

    @Autowired
    private WeatherDAO weatherDAO;

    /**
     * @param weather
     * listens to the message queue
     */
    @Transactional()
    @JmsListener(destination = "TestQ", containerFactory = "jmsConnectionFactory")
    public void handleMessage(Weather weather)  throws IOException, DataAccessError {
        LOG.info("get the object Weather for queue and call method DAO for save it: {}", weather);

        weatherDAO.saveWeather(weather);
    }
}
