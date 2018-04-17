package ru.bellintegrator.wb.dao;

import ru.bellintegrator.wb.exception.DataAccessError;
import ru.bellintegrator.wb.model.Weather;

/**
 * @author Хмель А.В.
 */
public interface WeatherDAO {
    /**
     * @param city
     * @return Weather
     * @throws DataAccessError If an exception access data
     */
    Weather getWeather(String city) throws DataAccessError;

    /**
     * @param weather
     * @throws DataAccessError If an exception access data
     */
    void saveWeather(Weather weather) throws DataAccessError;
}
