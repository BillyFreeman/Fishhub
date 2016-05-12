/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "daily_weather")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DailyWeather implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "day_number", nullable = false)
    @NotNull
    private Integer dayNumber;
    @Column(name = "forecast_date")
    private Date forecastDate;
    @Column(name = "min_temperature")
    private Integer minTemperature;
    @Column(name = "max_temperature")
    private Integer maxTemperature;
    @Column(name = "humidity")
    private Integer humidity;
    @Column(name = "athm_pressure")
    private Integer pressure;
    @Column(name = "moon_phase")
    private Integer moonPhase;
    @Column(name = "moon_phase_name")
    private String moonPhaseName;
    @ManyToOne(fetch = FetchType.LAZY) //related location
    @JoinColumn(name = "location_id", nullable = false)
    @XmlTransient
    @JsonIgnore
    private Location location;
    @OneToMany(mappedBy = "dailyWeather", fetch = FetchType.LAZY) //datailed (h3) weather table relation
    @Cascade(CascadeType.ALL)
    private List<H3PeriodWeather> h3WeatherList;

    public DailyWeather() {
    }

    //because of big amount of fields we use builder pattern instead of constructor with many arguments
    public static class DailyWeatherBuilder {

        private Integer dayNumber;
        private Date forecastDate;
        private Integer minTemperature;
        private Integer maxTemperature;
        private Integer humidity;
        private Integer pressure;
        private Integer moonPhase;
        private String moonPhaseName;
        private Location location;
        private List<H3PeriodWeather> h3WeatherList;

        public DailyWeatherBuilder() {
        }

        public DailyWeatherBuilder addDayNumber(Integer dayNumber) {
            this.dayNumber = dayNumber;
            return this;
        }

        public DailyWeatherBuilder setForecastDate(Date forecastDate) {
            this.forecastDate = forecastDate;
            return this;
        }

        public DailyWeatherBuilder setMinTemperature(Integer minTemperature) {
            this.minTemperature = minTemperature;
            return this;
        }

        public DailyWeatherBuilder setMaxTemperature(Integer maxTemperature) {
            this.maxTemperature = maxTemperature;
            return this;
        }

        public DailyWeatherBuilder setHumidity(Integer humidity) {
            this.humidity = humidity;
            return this;
        }

        public DailyWeatherBuilder setPressure(Integer pressure) {
            this.pressure = pressure;
            return this;
        }

        public DailyWeatherBuilder setMoonPhase(Integer moonPhase) {
            this.moonPhase = moonPhase;
            return this;
        }

        public DailyWeatherBuilder setMoonPhaseName(String moonPhaseName) {
            this.moonPhaseName = moonPhaseName;
            return this;
        }

        public DailyWeatherBuilder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public DailyWeatherBuilder setH3List(List<H3PeriodWeather> h3WeatherList) {
            this.h3WeatherList = h3WeatherList;
            return this;
        }

        public DailyWeather build() {
            DailyWeather weather = new DailyWeather();
            weather.dayNumber = dayNumber;
            weather.forecastDate = forecastDate;
            weather.minTemperature = minTemperature;
            weather.maxTemperature = maxTemperature;
            weather.humidity = humidity;
            weather.pressure = pressure;
            weather.moonPhase = moonPhase;
            weather.moonPhaseName = moonPhaseName;
            weather.location = location;
            weather.h3WeatherList = h3WeatherList;
            return weather;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(Integer moonPhase) {
        this.moonPhase = moonPhase;
    }

    public String getMoonPhaseName() {
        return moonPhaseName;
    }

    public void setMoonPhaseName(String moonPhaseName) {
        this.moonPhaseName = moonPhaseName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<H3PeriodWeather> getH3WeatherList() {
        return h3WeatherList;
    }

    public void setH3WeatherList(List<H3PeriodWeather> h3WeatherList) {
        this.h3WeatherList = h3WeatherList;
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
            return this.id.equals(((DailyWeather) obj).getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int BASE = 14;
        int result = 1;
        result = BASE * result + this.id;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("DailyWeather:\t");
        builder
                .append("id: ").append(this.id).append("; ")
                .append("forecastDate: ").append(this.forecastDate).append("\n\t")
                .append("dayNumber: ").append(this.dayNumber).append("\n\t")
                .append("minTemperature: ").append(this.minTemperature).append("\n\t")
                .append("maxTemperature: ").append(this.maxTemperature).append("\n\t")
                .append("pressure: ").append(this.pressure).append("\n\t")
                .append("humidity: ").append(this.humidity).append("\n\t")
                .append("moonPhase: ").append(this.moonPhase).append("\n\t")
                .append("moonPhaseName: ").append(this.moonPhaseName).append("\n");
        for (H3PeriodWeather h3 : h3WeatherList) {
            builder.append(h3.toString()).append("\n");
        }
        return builder.toString();
    }
}
