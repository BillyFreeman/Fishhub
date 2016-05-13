/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi;

import com.victor.fishhub.dao.entity.DailyWeather;
import com.victor.fishhub.dao.entity.H3PeriodWeather;
import com.victor.fishhub.service.moonphase.MoonPhase;
import com.victor.fishhub.service.moonphase.MoonPhaseHelper;
import com.victor.fishhub.service.weatherapi.exception.WeatherDataFormatException;
import com.victor.fishhub.service.weatherapi.rawentity.Time;
import com.victor.fishhub.service.weatherapi.rawentity.WeatherData;
import com.victor.fishhub.service.wind.WindDirectionHelper;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("responseConverter")
class APIResponseConverterImpl implements APIResponseConverter {

    @Autowired
    @Qualifier("simpleMoonHelper")
    private MoonPhaseHelper moonHelper;

    @Autowired
    @Qualifier("simpleWindHelper")
    private WindDirectionHelper windHelper;

    //for date parsing
    private final static String DATE_PATTERN = "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}$";
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private Pattern datePattern;
    private SimpleDateFormat dateFormat;

    Map<String, String> weatherCodes;

    public APIResponseConverterImpl() {
        datePattern = Pattern.compile(DATE_PATTERN);
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    public APIResponseConverterImpl(Map<String, String> weatherCodes) {
        this();
        this.weatherCodes = weatherCodes; //weather codes map inject
    }

    @Override
    public List<DailyWeather> updateDailyWeatherList(WeatherData data, List<DailyWeather> weatherList) throws WeatherDataFormatException {
        List<H3PeriodWeather> h3List = convertToH3PeriodWeatherList(data);

        if (h3List.size() != 40) { //if response don't contain complete data
            throw new WeatherDataFormatException();
        }

        //devide list into 5 periods and sequentially update each corresponding entity
        for (int i = 0; i < 5; i++) {
            updateDailyWeather(h3List.subList(0 + 8 * i, 8 + 8 * i), weatherList.get(i)).setDayNumber(i + 1);
        }
        return weatherList;
    }

    //converts weather api resopnse to entity objects
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

            int dir = (int) t.getWindDirrection().getDegree();
            hpw.setWindDirection(dir);
            hpw.setWindName(windHelper.getWindName(dir));

            h3List.add(hpw);
        }
        return h3List;
    }

    //returns the same DailyWeagher instance that it gets as an argument
    private DailyWeather updateDailyWeather(List<H3PeriodWeather> periods, DailyWeather weather) {

        int min, max, hum, press;
        hum = press = 0;
        min = max = periods.get(0).getTemperature();

        //find max, min and average daily values from 3 hour periods (h3)
        for (int i = 0; i < periods.size(); i++) {
            H3PeriodWeather h3 = periods.get(i);
            min = h3.getTemperature() < min ? h3.getTemperature() : min; //find min temperature
            max = h3.getTemperature() > max ? h3.getTemperature() : max; //find max temperature
            hum += h3.getHumidity();
            press += h3.getPressure();
            h3.setId(weather.getH3WeatherList().get(i).getId()); //set id from the old h3 instance
            h3.setDailyWeather(weather);
        }

        hum /= periods.size(); //calculate average daily temperature
        press /= periods.size(); //calculate average daily atmospheric pressure

        /*
         As h3 periods can have different date, so for DailyWeather instance we will take date of fourth period
         We will use this date to get current moon phase
         */
        Date forecastDate = periods.get(3).getForecastDate();
        MoonPhase phase = moonHelper.getPhase(forecastDate);

        weather.setForecastDate(forecastDate);
        weather.setMinTemperature(min);
        weather.setMaxTemperature(max);
        weather.setHumidity(hum);
        weather.setPressure(press);
        weather.setMoonPhase(phase.getDay());
        weather.setMoonPhaseName(phase.getName());
        weather.setH3WeatherList(periods); //set new h3 instances

        phase = null;
        forecastDate = null;

        return weather;
    }

    //converts data time/date string to sql.Date object
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

    //converts data time/date string to sql.Time object
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

    //convert Fahrenheit to Celsius
    private int getCelsiusTemperature(float t) {
        if (t > 100) {
            t -= 273.15f;
        }
        return Math.round(t);
    }

    //gets weather type name by code
    private String getWeatherType(String code) {
        if (weatherCodes != null) {
            String weatherName = weatherCodes.get(code);
            return weatherName != null ? weatherName : "not specified";
        }
        return null;
    }
}
