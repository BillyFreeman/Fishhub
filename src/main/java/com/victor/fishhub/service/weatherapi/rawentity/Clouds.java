/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi.rawentity;

import java.io.Serializable;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clouds")
@XmlAccessorType(XmlAccessType.FIELD)
public class Clouds implements Serializable {

    @XmlAttribute(name = "value")
    private String value;
    @XmlAnyAttribute
    private Map<String, Object> nodes;

    public Clouds() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, Object> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Object> nodes) {
        this.nodes = nodes;
    }
}
