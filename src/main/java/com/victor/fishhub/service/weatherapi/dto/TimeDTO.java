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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Element;

@XmlRootElement(name = "time")
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeDTO implements Serializable {

    @XmlAttribute(name = "from")
    private String from;
    @XmlAttribute(name = "to")
    private String to;

    private SymbolDTO symbol;
    private WindDirectionDTO windDirection;
    private WindSpeedDTO windSpeed;
    private TemperatureDTO temperature;
    private PressureDTO pressure;
    private HumidityDTO humidity;
    private CloudsDTO clouds;

    @XmlAnyElement
    private List<Element> nodes;

    public TimeDTO() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public SymbolDTO getSymbol() {
        return symbol;
    }

    public void setSymbol(SymbolDTO symbol) {
        this.symbol = symbol;
    }

    public WindDirectionDTO getWindDirrection() {
        return windDirection;
    }

    public void setWindDirrection(WindDirectionDTO windDirrection) {
        this.windDirection = windDirrection;
    }

    public WindSpeedDTO getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(WindSpeedDTO windSpeed) {
        this.windSpeed = windSpeed;
    }

    public TemperatureDTO getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureDTO temperature) {
        this.temperature = temperature;
    }

    public PressureDTO getPressure() {
        return pressure;
    }

    public void setPressure(PressureDTO pressure) {
        this.pressure = pressure;
    }

    public HumidityDTO getHumidity() {
        return humidity;
    }

    public void setHumidity(HumidityDTO humidity) {
        this.humidity = humidity;
    }

    public CloudsDTO getClouds() {
        return clouds;
    }

    public void setClouds(CloudsDTO clouds) {
        this.clouds = clouds;
    }

    public List<Element> getNodes() {
        return nodes;
    }

    public void setNodes(List<Element> nodes) {
        this.nodes = nodes;
    }
}
