package ru.bellintegrator.wb.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.wb.dao.WeatherDAO;
import ru.bellintegrator.wb.exception.DataAccessError;
import ru.bellintegrator.wb.model.Weather;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;

/**
 * @author Хмель А.В.
 * class DAO for work with Weather
 */
@Repository
public class WeatherDAOImpl implements WeatherDAO {
    private final Logger LOG = LoggerFactory.getLogger(WeatherDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    // get the object Weather by city
    @Override
    public Weather getWeather(String city) throws DataAccessError {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Weather> criteria = builder.createQuery(Weather.class);

            Root<Weather> root = criteria.from(Weather.class);
            criteria.where(builder.equal(root.get("city"), city));
            criteria.orderBy(builder.desc(root.get("created")));

            TypedQuery<Weather> query = entityManager.createQuery(criteria).setMaxResults(1);
            Weather weather = query.getSingleResult();

            LOG.info("get the object Weather by city");

            return weather != null ? weather : new Weather();
        } catch (Exception ex) {
            throw new DataAccessError("Data access error in the method of the getWeather");
        }
    }

    // save the object Weather
    @Override
    public void saveWeather(Weather weather) throws DataAccessError {
        try {
            entityManager.persist(weather);
            LOG.info("save the object Weather");
        } catch (Exception ex) {
            throw new DataAccessError("Data access error in the method of the saveWeather");
        }
    }
}
