/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastDTO {

    @XmlElement(name = "time")
    List<TimeDTO> time;

    public ForecastDTO() {
    }

    public List<TimeDTO> getTime() {
        return time;
    }

    public void setTime(List<TimeDTO> time) {
        this.time = time;
    }

}
