/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi.dto;

import com.victor.fishhub.service.weatherapi.dto.adapter.FloatToIntegerAdapter;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "pressure")
@XmlAccessorType(XmlAccessType.FIELD)
public class PressureDTO implements Serializable {

    @XmlAttribute(name = "unit")
    private String unit;
    @XmlAttribute(name = "value")
    @XmlJavaTypeAdapter(FloatToIntegerAdapter.class)
    private Integer value;

    public PressureDTO() {
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
