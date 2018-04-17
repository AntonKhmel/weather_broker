package ru.bellintegrator.wb.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Хмель А.В.
 * entity Weather
 */
@Entity
@Table(name = "weather")
public class Weather implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // the service field of the hibernate for version control
    @Version
    private Integer version;

    @Basic(optional = false)
    @Size(min = 2, max = 20)
    @Column(name = "city")
    private String city;

    @Column(name = "count")
    private int count;

    @Basic(optional = false)
    @Column(name = "created")
    private Date created;

    @Basic(optional = false)
    @Size(min = 2, max = 15)
    @Column(name = "lang")
    private String lang;

    @Column(name = "chill")
    private int chill;

    @Basic(optional = false)
    @Column(name = "direction")
    private int direction;

    @Basic(optional = false)
    @Column(name = "speed")
    private int speed;

    public Weather(String city) {
        this.city = city;
    }

    public Weather(String city, int count, String created, String lang, int chill, int direction, int speed) {
        this(city);
        this.count = count;
        if (created != null && !created.trim().isEmpty()) {
            try {
                this.created = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(created);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.lang = lang;
        this.chill = chill;
        this.direction = direction;
        this.speed = speed;
    }

    /**
     * constructor for the hibernate
     */
    public Weather() {
    }

    // getters and setters methods

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(String created) {
        if (created != null && !created.trim().isEmpty()) {
            try {
                this.created = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(created);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getChill() {
        return chill;
    }

    public void setChill(int chill) {
        this.chill = chill;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

// equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        if (id != this.id) return false;
        if (version != this.version) return false;
        if (city != null ? !city.equals(this.city) : this.city != null) return false;
        if (created != null ? !created.equals(this.created) : this.created != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 + (city != null ? city.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + version;

        return result;
    }

    /**
     * @return the string representation of the object Weather
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Weather:");
        builder.append("{id: ");
        builder.append(getId());
        builder.append("; city: ");
        builder.append(getCity());
        builder.append("; count: ");
        builder.append(getCount());
        builder.append("; created: ");
        builder.append(getCreated());
        builder.append("; lang: ");
        builder.append(getLang());
        builder.append("; chill: ");
        builder.append(getChill());
        builder.append("; direction: ");
        builder.append(getDirection());
        builder.append("; speed: ");
        builder.append(getSpeed());
        builder.append("}");

        return builder.toString();
    }
}
