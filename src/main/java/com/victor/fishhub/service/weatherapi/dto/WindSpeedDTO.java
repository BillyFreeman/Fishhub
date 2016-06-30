/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Element;

@XmlRootElement(name = "windSpeed")
@XmlAccessorType(XmlAccessType.FIELD)
public class WindSpeedDTO implements Serializable {

    @XmlAttribute(name = "mps")
    private double speed;
    @XmlAnyAttribute
    private Map<String, Object> nodes;

    public WindSpeedDTO() {
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Map<String, Object> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Object> nodes) {
        this.nodes = nodes;
    }

}
