/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi;

import com.victor.fishhub.dao.entity.DailyWeather;
import com.victor.fishhub.dao.entity.H3PeriodWeather;
import com.victor.fishhub.service.weatherapi.exception.WeatherDataFormatException;
import com.victor.fishhub.service.weatherapi.rawentity.WeatherData;
import java.util.List;


public interface APIResponseConverter {

    List<DailyWeather> updateDailyWeatherList(WeatherData data, List<DailyWeather> weatherList) throws WeatherDataFormatException;

    List<H3PeriodWeather> convertToH3PeriodWeatherList(WeatherData data);

}
