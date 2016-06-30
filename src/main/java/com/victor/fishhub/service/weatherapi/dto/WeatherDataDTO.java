/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Element;

@XmlRootElement(name = "weatherdata")
@XmlAccessorType(XmlAccessType.FIELD)
public class WeatherDataDTO implements Serializable {
    
    private LocationDTO location;
    @XmlElement(name = "forecast")
    private ForecastDTO forecast;
    @XmlAnyElement
    private List<Element> nodes;

    public WeatherDataDTO() {
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public List<Element> getNodes() {
        return nodes;
    }

    public void setNodes(List<Element> nodes) {
        this.nodes = nodes;
    }

    public ForecastDTO getForecast() {
        return forecast;
    }

    public void setForecast(ForecastDTO forecast) {
        this.forecast = forecast;
    }
}
