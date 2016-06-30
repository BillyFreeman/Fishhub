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
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Element;

@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationDTO implements Serializable {

    private String name;
    private String country;
    
    @XmlAnyElement
    private List<Element> nodes;


    public LocationDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public List<Element> getNodes() {
        return nodes;
    }

    public void setNodes(List<Element> nodes) {
        this.nodes = nodes;
    }
}
