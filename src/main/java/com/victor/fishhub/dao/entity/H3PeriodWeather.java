/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "h3_period_weather")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class H3PeriodWeather implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "forecast_date")
    private Date forecastDate;
    @Column(name = "forecast_time")
    private Time forecastTime;
    @Column(name = "h3_period_temperature")
    private Integer temperature;
    @Column(name = "h3_period_weather")
    private String weatherType;
    @Column(name = "h3_period_weather_code")
    private String weatherCode;
    @Column(name = "athm_pressure")
    private Integer pressure;
    @Column(name = "humidity")
    private Integer humidity;
    @Column(name = "h3_wind_speed")
    private Integer windSpeed;
    @Column(name = "h3_wind_direction")
    private Integer windDirection;
    @Column(name = "h3_wind_name")
    private String windName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_id", nullable = false)
    @XmlTransient
    @JsonIgnore
    private DailyWeather dailyWeather;

    public H3PeriodWeather() {
    }
    
    
    //there should be builder pattern instead folowing constructor
    public H3PeriodWeather(
            Date forecastDate,
            Time forecastTime,
            Integer temperature,
            String weatherType,
            String weatherCode,
            Integer pressure,
            Integer humidity,
            DailyWeather dailyWeather,
            Integer windSpeed,
            Integer windDirection,
            String windName
    ) {
        this.forecastDate = forecastDate;
        this.forecastTime = forecastTime;
        this.temperature = temperature;
        this.weatherType = weatherType;
        this.weatherCode = weatherCode;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dailyWeather = dailyWeather;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.windName = windName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }

    public Time getForecastTime() {
        return forecastTime;
    }

    public void setForecastTime(Time forecastTime) {
        this.forecastTime = forecastTime;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public DailyWeather getDailyWeather() {
        return dailyWeather;
    }

    public void setDailyWeather(DailyWeather dailiWeather) {
        this.dailyWeather = dailiWeather;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindName() {
        return windName;
    }

    public void setWindName(String windName) {
        this.windName = windName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() == obj.getClass()) {
            return this.id.equals(((H3PeriodWeather) obj).getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int BASE = 15;
        int result = 1;
        result = BASE * result + this.id;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("H3:\t");
        builder
                .append("id: ").append(this.id).append("; ")
                .append("forecastDate: ").append(this.forecastDate).append("; ")
                .append("forecastTime: ").append(this.forecastTime).append("\n\t")
                .append("temperature: ").append(this.temperature).append("\n\t")
                .append("pressure: ").append(this.pressure).append("\n\t")
                .append("humidity: ").append(this.humidity).append("\n\t")
                .append("weatherType: ").append(this.weatherType).append("\n\t")
                .append("weatherCode: ").append(this.weatherCode).append("\n\t")
                .append("windSpeed: ").append(this.windSpeed).append("\n\t")
                .append("windDirection: ").append(this.windDirection).append("\n\t")
                .append("windName: ").append(this.windName).append("\n");
        return builder.toString();
    }
}
