/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("apiHelper")
class APIHelperImpl implements APIHelper {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?";
    
    @Value("${api_mode}")
    private String apiMode;
    @Value("${api_units}")
    private String apiUnits;
    @Value("${api_key}")
    private String apiKey;

    public APIHelperImpl() {
    }

    public String getURLString(double lat, double lon) {
        StringBuilder builder = new StringBuilder(BASE_URL);
        builder.append("lat=").append(lat).append('&');
        builder.append("lon=").append(lon).append('&');
        builder.append("mode=").append(apiMode).append('&');
        builder.append("units=").append(apiUnits).append('&');
        builder.append("APPID=").append(apiKey);
        System.out.println(builder.toString());
        return builder.toString();
    }
}
