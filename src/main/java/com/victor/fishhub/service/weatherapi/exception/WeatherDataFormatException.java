/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi.exception;


public class WeatherDataFormatException extends Exception {
    
    private final static String H3_MESSAGE = "API response has wrong number of 3 hour periods";
    
    public WeatherDataFormatException(){
        super(H3_MESSAGE);
    }
    
    public WeatherDataFormatException(String message) {
        super(message);
    }
    
}
