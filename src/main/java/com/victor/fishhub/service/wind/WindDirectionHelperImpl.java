package com.victor.fishhub.service.wind;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component("simpleWindHelper")
class WindDirectionHelperImpl implements WindDirectionHelper {

    private Map<String, Double> directonMap;

    public WindDirectionHelperImpl() {
    }

    public WindDirectionHelperImpl(Map<String, Double> directonMap) {
        this.directonMap = directonMap;
    }

    @Override
    public String getWindName(int direction) {
        for (Map.Entry<String, Double> e : directonMap.entrySet()) {
            if (Math.abs(direction - e.getValue()) <= 11.25f) { //look for nearest predefined value to current wind direction
                return e.getKey();
            }
        }
        return "North";
    }
}
