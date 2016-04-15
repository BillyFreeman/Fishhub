package com.victor.fishhub.service.wind;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component("simpleWindHelper")
class WindDirectionHelperImpl implements WindDirectionHelper {

    private static final Map<String, Float> BASE_MAP;

    static {
        BASE_MAP = new HashMap<>();
        BASE_MAP.put("North", 0f);
        BASE_MAP.put("North-NorthEast", 22.5f);
        BASE_MAP.put("NorthEast", 45f);
        BASE_MAP.put("East-NorthEast", 67.5f);
        BASE_MAP.put("East", 90f);
        BASE_MAP.put("East-SouthEast", 112.5f);
        BASE_MAP.put("SouthEast", 135f);
        BASE_MAP.put("South-SouthEast", 157.5f);
        BASE_MAP.put("South", 180f);
        BASE_MAP.put("South-SouthWest", 202.5f);
        BASE_MAP.put("SouthWest", 225f);
        BASE_MAP.put("West-SouthWest", 247.5f);
        BASE_MAP.put("West", 270f);
        BASE_MAP.put("West-NorthWest", 292.5f);
        BASE_MAP.put("NorthWest", 315f);
        BASE_MAP.put("North-NorthWest", 337.5f);
    }

    public WindDirectionHelperImpl() {
    }

    @Override
    public String getWindName(int direction) {
        for (Map.Entry<String, Float> e : BASE_MAP.entrySet()) {
            if (Math.abs(direction - e.getValue()) <= 11.25f) {
                return e.getKey();
            }
        }
        return "North";
    }
}
