/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.weatherapi.dto.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FloatToIntegerAdapter extends XmlAdapter<Float, Integer> {

    @Override
    public Integer unmarshal(Float v) throws Exception {
        return Math.round(v);
    }

    @Override
    public Float marshal(Integer v) throws Exception {
        return v.floatValue();
    }

}
