/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.dao.entity.converter;

import javax.persistence.AttributeConverter;


public class DoubleToStringConverter implements AttributeConverter<Double, String>{

    @Override
    public String convertToDatabaseColumn(Double attribute) {
        return String.valueOf(attribute);
    }

    @Override
    public Double convertToEntityAttribute(String dbData) {
        return Double.parseDouble(dbData);
    }
    
}
