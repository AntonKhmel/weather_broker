package ru.bellintegrator.wb.model.view;

import java.util.Objects;

public class WeatherView {
    private String city;
    private String value;

    /**
     * constructor for class the WeatherView
     */
    public WeatherView(String city, String value) {
        this.city = city;
        this.value = value;
    }

    public WeatherView() {
    }

    // getter method

    public String getCity() {
        return city;
    }

    public String getValue() {
        return value;
    }

    // equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherView weatherView = (WeatherView) o;
        if (city != null ? !city.equals(this.city) : this.city != null) return false;
        if (value != null ? !value.equals(this.value) : this.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31  + (city != null ? city.hashCode() : 0);
        result = result * 31  + (value != null ? value.hashCode() : 0);

        return result;
    }

    /**
     * @return the string representation of the object WeatherView
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WeatherView:");
        builder.append("{city: ");
        builder.append(getCity());
        builder.append("; value: ");
        builder.append(getValue());
        builder.append("}");

        return builder.toString();
    }
}
