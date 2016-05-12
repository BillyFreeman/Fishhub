package com.victor.fishhub.service.wind;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component("simpleWindHelper")
class WindDirectionHelperImpl implements WindDirectionHelper {

    private Map<String, Double> directonMap;

    public WindDirectionHelperImpl() {
    }

    @Override
    public String getWindName(int direction) {
        for (Map.Entry<String, Double> e : directonMap.entrySet()) {
            if (Math.abs(direction - e.getValue()) <= 11.25f) {
                return e.getKey();
            }
        }
        return "North";
    }

    public void setDirectonMap(Map<String, Double> directonMap) {
        this.directonMap = directonMap;
    }
}
