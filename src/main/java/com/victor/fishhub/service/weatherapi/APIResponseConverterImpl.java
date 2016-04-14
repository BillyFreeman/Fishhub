/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi;

import com.victor.fishhub.dao.entity.DailyWeather;
import com.victor.fishhub.dao.entity.H3PeriodWeather;
import com.victor.fishhub.service.weatherapi.exception.WeatherDataFormatException;
import com.victor.fishhub.service.weatherapi.rawentity.Time;
import com.victor.fishhub.service.weatherapi.rawentity.WeatherData;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.stereotype.Component;

@Component("responseConverter")
class APIResponseConverterImpl implements APIResponseConverter {
    
    @Autowired
    @Qualifier("allProps")
    PropertiesFactoryBean propsFactory;

    private final static String DATE_PATTERN = "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}$";
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private Properties properties;
    private Pattern datePattern;
    private SimpleDateFormat dateFormat;

    private int moonPhasePercent;

    public APIResponseConverterImpl() {
    }

    @PostConstruct
    private void init() {
        try {
            properties = propsFactory.getObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        datePattern = Pattern.compile(DATE_PATTERN);
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    @Override
    public List<DailyWeather> updateDailyWeatherList(WeatherData data, List<DailyWeather> weatherList) throws WeatherDataFormatException {
        List<H3PeriodWeather> h3List = convertToH3PeriodWeatherList(data);

        if (h3List.size() != 40) {
            throw new WeatherDataFormatException();
        }

        for (int i = 0; i < 5; i++) {
            DailyWeather dailyWeather = updateDailyWeather(h3List.subList(0 + 8 * i, 8 + 8 * i), weatherList.get(i));
            dailyWeather.setDayNumber(i + 1);
        }

        return weatherList;
    }

    @Override
    public List<H3PeriodWeather> convertToH3PeriodWeatherList(WeatherData data) {
        List<H3PeriodWeather> h3List = new ArrayList<>();
        for (Time t : data.getForecast().getTime()) {
            H3PeriodWeather hpw = new H3PeriodWeather();
            hpw.setForecastDate(getForecastDate(t.getTo()));
            hpw.setForecastTime(getForecastTime(t.getTo()));
            hpw.setTemperature(getCelsiusTemperature(t.getTemperature().getValue()));
            hpw.setWeatherType(getWeatherType(t.getSymbol().getVar()));
            hpw.setWeatherCode(t.getSymbol().getVar());
            hpw.setPressure(t.getPressure().getValue());
            hpw.setHumidity(t.getHumidity().getValue());
            hpw.setWindSpeed((int) t.getWindSpeed().getSpeed());
            hpw.setWindDirection((int) t.getWindDirrection().getDegree());
            h3List.add(hpw);
        }
        return h3List;
    }

    private DailyWeather updateDailyWeather(List<H3PeriodWeather> periods, DailyWeather weather) {

        int min, max, hum, press;
        hum = press = 0;
        min = max = periods.get(0).getTemperature();

        for (int i = 0; i < periods.size(); i++) {
            H3PeriodWeather h3 = periods.get(i);
            min = h3.getTemperature() < min ? h3.getTemperature() : min;
            max = h3.getTemperature() > max ? h3.getTemperature() : max;
            hum += h3.getHumidity();
            press += h3.getPressure();
            h3.setId(weather.getH3WeatherList().get(i).getId());
            h3.setDailyWeather(weather);
        }

        hum /= periods.size();
        press /= periods.size();

        weather.setForecastDate(periods.get(4).getForecastDate());
        weather.setMinTemperature(min);
        weather.setMaxTemperature(max);
        weather.setHumidity(hum);
        weather.setPressure(press);
        weather.setWindSpeed(periods.get(4).getWindSpeed());
        weather.setWindDirection(periods.get(4).getWindDirection());
        weather.setMoonPhase(moonPhasePercent);
        weather.setH3WeatherList(periods);

        return weather;
    }

    private Date getForecastDate(String date) {
        Matcher matcher = datePattern.matcher(date);
        if (matcher.matches()) {
            try {
                Date sqlDate = new Date(dateFormat.parse(date).getTime());
                return sqlDate;
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private java.sql.Time getForecastTime(String date) {
        Matcher matcher = datePattern.matcher(date);
        if (matcher.matches()) {
            try {
                java.sql.Time sqlTime = new java.sql.Time(dateFormat.parse(date).getTime());
                return sqlTime;
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private int getCelsiusTemperature(float t) {
        if (t > 100) {
            t -= 273.15f;
        }
        return Math.round(t);
    }

    private String getWeatherType(String code) {
        if (properties != null) {
            String property = properties.getProperty(code, "not specified");
            return property;
        }
        return null;
    }
}
