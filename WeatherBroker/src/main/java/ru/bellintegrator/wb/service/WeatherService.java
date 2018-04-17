package ru.bellintegrator.wb.service;

import ru.bellintegrator.wb.exception.DataAccessError;
import ru.bellintegrator.wb.model.Weather;
import ru.bellintegrator.wb.model.view.WeatherView;

/**
 * @author Хмель А.В.
 */
public interface WeatherService {
    /**
     * @param city
     * @throws DataAccessError If an exception access data
     */
    void getWeatherYahoo(String city) throws DataAccessError;

    /**
     * @param city
     * @return WeatherView
     * @throws DataAccessError If an exception access data
     */
    WeatherView getWeather(String city) throws DataAccessError;

    /**
     * @param weather
     * @throws DataAccessError If an exception access data
     */
    void sendMessage(final Weather weather) throws DataAccessError;
}
