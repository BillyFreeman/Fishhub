/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "symbol")
@XmlAccessorType(XmlAccessType.FIELD)
public class SymbolDTO implements Serializable {
    
    @XmlAttribute(name = "number")
    private int number;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "var")
    private String var;
    
    public SymbolDTO(){
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
