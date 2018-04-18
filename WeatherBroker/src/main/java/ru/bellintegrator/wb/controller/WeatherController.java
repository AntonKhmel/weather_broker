package ru.bellintegrator.wb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.wb.exception.DataAccessError;
import ru.bellintegrator.wb.model.Weather;
import ru.bellintegrator.wb.model.view.WeatherView;
import ru.bellintegrator.wb.service.WeatherService;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Хмель А.В.
 * class Controller for processing requests associated with the object Weather and calling business logic
 */
@RestController
@RequestMapping(value = "/weather", produces = APPLICATION_JSON_VALUE)
public class WeatherController {
    private final Logger LOG = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper mapper;

    /**
     * @param reqWeather
     * @return HttpStatus
     */
    @RequestMapping(value = "/yahoo", method = RequestMethod.POST)
    public HttpStatus getWeatherYahoo(@RequestBody WeatherView reqWeather) throws DataAccessError {
        LOG.info("get and send in jms queue the object Weather from json by city: {}", reqWeather.getCity());

        if (reqWeather.getCity() != null && !reqWeather.getCity().trim().isEmpty()) {
            weatherService.getWeatherYahoo(reqWeather.getCity());
        } else {
            return HttpStatus.NO_CONTENT;
        }

        return HttpStatus.CREATED;
    }

    /**
     * @param reqWeather
     * @return WeatherView
     */
    @RequestMapping(value = "/db", method = RequestMethod.POST)
    public WeatherView getWeatherDb(@RequestBody WeatherView reqWeather) throws DataAccessError {
        WeatherView weatherView = null;

        LOG.info("get json by city: {}", reqWeather.getCity());

        if (reqWeather.getCity() != null && !reqWeather.getCity().trim().isEmpty()) {
            weatherView = weatherService.getWeather(reqWeather.getCity());
        }

        return weatherView;
    }

    /**
     * @param exception
     * @return ResponseEntity<String>
     */
    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception exception) {
        LOG.info("exception: {}", exception);

        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
}
