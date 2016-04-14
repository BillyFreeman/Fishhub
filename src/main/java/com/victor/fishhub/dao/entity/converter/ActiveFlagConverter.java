/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.dao.entity.converter;

import javax.persistence.AttributeConverter;

/**
 *
 * @author Віктор
 */
public class ActiveFlagConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? "true" : "false";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return Boolean.parseBoolean(dbData);
    }

}
