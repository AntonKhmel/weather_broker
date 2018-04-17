package ru.bellintegrator.wb.service.json.impl;

import com.fasterxml.jackson.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.wb.exception.DataAccessError;
import ru.bellintegrator.wb.model.Weather;
import ru.bellintegrator.wb.model.view.WeatherView;
import ru.bellintegrator.wb.service.json.Convert;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

/**
 * @author Хмель А.В.
 * class Service for object Weather by work with business logic
 */
@Service
public class ConvertImpl implements Convert {
    private final Logger LOG = LoggerFactory.getLogger(ConvertImpl.class);

    @Autowired
    private JsonFactory jsonFactory;

    // converts the object Weather in the WeatherView(json)
    // It was possible to use ObjectWriter,
    // but to get a json equivalent to the service yahoo had to do so.
    @Override
    public WeatherView weatherToJson(Weather weather) throws DataAccessError {
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            try (JsonGenerator jsonGenerator = jsonFactory
                    .createGenerator(outputStream, JsonEncoding.UTF8)) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName("query");
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("count", weather.getCount());
                jsonGenerator.writeStringField("created", weather.getCreated() != null ?
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                                .format(weather.getCreated()) : " ");
                jsonGenerator.writeStringField("lang", weather.getLang());
                jsonGenerator.writeFieldName("results");
                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName("channel");
                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName("wind");
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("chill", Integer.toString(weather
                        .getChill()));
                jsonGenerator.writeStringField("direction", Integer.toString(weather
                        .getDirection()));
                jsonGenerator.writeStringField("speed", Integer.toString(weather
                        .getSpeed()));
                jsonGenerator.writeEndObject();
                jsonGenerator.writeEndObject();
                jsonGenerator.writeEndObject();
                jsonGenerator.writeEndObject();
                jsonGenerator.writeEndObject();
            }
        } catch (Exception ex) {
            throw new DataAccessError("Data access error in the method of the weatherToJson", ex);
        }

        LOG.info("converts the object Weather in the WeatherView(json)");

        return new WeatherView(weather.getCity(), new String(((ByteArrayOutputStream) outputStream).toByteArray()));
    }

    // converts json as a string in the object Weather
    @Override
    public Weather jsonToWeather(String json, String city) throws DataAccessError {
        Weather weather = new Weather();
        try {
            JsonParser jsonParser = jsonFactory.createParser(json);
            while (!jsonParser.isClosed()) {
                JsonToken jsonToken = jsonParser.nextToken();

                weather.setCity(city);

                if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                    String fieldName = jsonParser.getCurrentName();
                    jsonToken = jsonParser.nextToken();
                    if (null != fieldName) {
                        switch (fieldName) {
                            case "count":
                                weather.setCount(jsonParser.getValueAsInt());
                                break;
                            case "created":
                                weather.setCreated(jsonParser.getValueAsString());
                                break;
                            case "lang":
                                weather.setLang(jsonParser.getValueAsString());
                                break;
                            case "chill":
                                weather.setChill(jsonParser.getValueAsInt());
                                break;
                            case "direction":
                                weather.setDirection(jsonParser.getValueAsInt());
                                break;
                            case "speed":
                                weather.setSpeed(jsonParser.getValueAsInt());
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }  catch (Exception ex) {
            throw new DataAccessError("Data access error in the method of the jsonToWeather", ex);
        }

        LOG.info("converts json as a string in the object Weather");

        return weather;
    }
}
