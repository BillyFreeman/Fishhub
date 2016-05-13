/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.ratioalgo;

import com.victor.fishhub.dao.entity.DailyWeather;
import com.victor.fishhub.dao.entity.Fish;
import com.victor.fishhub.dao.entity.H3PeriodWeather;
import com.victor.fishhub.dao.entity.Location;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component("fishRatio")
class FishRatioImpl implements FishRatio {

    private final static Pattern PATTERN = Pattern.compile("[:-]");

    @Override
    public Location calculate(Location location) {
        for (Fish f : location.getFishlist()) {
            Map<String, Integer> ratioMap = new HashMap<>();
            for (DailyWeather dw : location.getWeatherList()) {
                for (H3PeriodWeather h3 : dw.getH3WeatherList()) {
                    String key = createRatioKey(h3.getForecastDate(), h3.getForecastTime());
                    ratioMap.put(key, 5);
                }
            }
            f.setActivityRatio(ratioMap);
        }
        return location;
    }

    private String createRatioKey(Date date, Time time) {
        String raw = "D" + date + "T" + time;
        Matcher matcher = PATTERN.matcher(raw);
        String key = matcher.replaceAll("");
        return key;
    }

    //dummy algorythm
    private Integer getRatioValue(H3PeriodWeather h3, Fish fish) {
        Integer ratio = null;
        if (h3 != null && fish != null) {
            ratio = (int) ((h3.getTemperature() / 25) * 5 + Math.random() * 5);
            ratio = ratio > 10 ? 10 : ratio;
        }
        return ratio;
    }
}
