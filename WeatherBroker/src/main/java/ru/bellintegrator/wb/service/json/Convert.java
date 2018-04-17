package ru.bellintegrator.wb.service.json;

import ru.bellintegrator.wb.exception.DataAccessError;
import ru.bellintegrator.wb.model.Weather;
import ru.bellintegrator.wb.model.view.WeatherView;

import java.io.OutputStream;

/**
 * @author Хмель А.В.
 */
public interface Convert {
    /**
     * @param weather
     * @return WeatherView
     * @throws DataAccessError If an exception access data
     */
    WeatherView weatherToJson(Weather weather) throws DataAccessError;

    /**
     * @param json
     * @param city
     * @return Weather
     * @throws DataAccessError If an exception access data
     */
    Weather jsonToWeather(String json, String city) throws DataAccessError;
}
