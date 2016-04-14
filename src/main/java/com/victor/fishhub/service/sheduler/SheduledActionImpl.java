/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.sheduler;

import com.victor.fishhub.dao.entity.Location;
import com.victor.fishhub.service.ServiceLayer;
import com.victor.fishhub.service.weatherapi.APIHelper;
import com.victor.fishhub.service.weatherapi.APIResponseConverter;
import com.victor.fishhub.service.weatherapi.exception.WeatherDataFormatException;
import com.victor.fishhub.service.weatherapi.rawentity.WeatherData;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SheduledActionImpl implements SheduledAction {

    @Autowired
    @Qualifier("fishhubService")
    private ServiceLayer service;

    @Autowired
    @Qualifier("apiHelper")
    private APIHelper apiHelper;

    @Autowired
    @Qualifier("responseConverter")
    private APIResponseConverter responseConverter;

    private Iterator<Location> locations;
    private SimpleDateFormat format;
    private long requestCount;

    private final static int MAX_REQUEST_NUMBER = 9000;

    public SheduledActionImpl() {
        format = new SimpleDateFormat("HH:mm:ss");
        requestCount = 0;
    }

    @Override
    @Scheduled(cron = "0 45 0 * * *", zone = "Europe/Helsinki")
    public void preExecution() {
        locations = service.getAllLocations().iterator();
    }

    @Override
    @Scheduled(cron = "0-57/1 * 1-3 * * *", zone = "Europe/Helsinki")
    public void executeApiRequest() {
        if (locations != null && locations.hasNext()) {
            Location nextLocation = locations.next();
            try {
                URL url = new URL(apiHelper.getURLString(nextLocation.getLatitude(), nextLocation.getLongtitude()));
                URLConnection connection = url.openConnection();

                JAXBContext context = JAXBContext.newInstance(WeatherData.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();

                WeatherData weatherData = (WeatherData) unmarshaller.unmarshal(connection.getInputStream());
                responseConverter.updateDailyWeatherList(weatherData, nextLocation.getWeatherList());
                nextLocation.setActive(true);
                service.updateLocation(nextLocation);
            } catch (MalformedURLException ex) {
                service.deactivateLocation(nextLocation.getId());
                ex.printStackTrace();
            } catch (IOException | JAXBException | WeatherDataFormatException ex) {
                service.deactivateLocation(nextLocation.getId());
                ex.printStackTrace();
            }
            requestCount++;
        }
    }

    @Override
    @Scheduled(cron = "0 15 3 * * *", zone = "Europe/Helsinki")
    public void postExecution() {
        List<Location> notActiveLocations;
        notActiveLocations = service.getNotActiveLocations();
        if (notActiveLocations.isEmpty()) {
            return;
        }
        locations = notActiveLocations.iterator();
        while (locations.hasNext()) {
            if (requestCount >= MAX_REQUEST_NUMBER) {
                locations = null;
                return;
            }
            executeApiRequest();
        }
        postExecution();
        locations = null;
        requestCount = 0;
    }
}
