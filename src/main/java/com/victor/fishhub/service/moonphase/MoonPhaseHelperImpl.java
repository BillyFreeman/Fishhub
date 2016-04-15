package com.victor.fishhub.service.moonphase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component("simpleMoonHelper")
class MoonPhaseHelperImpl implements MoonPhaseHelper {

    private static final double CYCLE = 29.530588853;
    private static long baseNewMoon;

    public MoonPhaseHelperImpl() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            baseNewMoon = format.parse("2016-03-09").getTime();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public MoonPhase getPhase(Date date) {
        double d = date.getTime() - baseNewMoon;
        double day = d % CYCLE;
        
        MoonPhase phase = new MoonPhase((int) day, getPhaseName(day));
        
        return phase;
    }

    private String getPhaseName(double day){
        if(day < 1.2){
            return "New moon";
        }
        if(day < 3){
            return "Young moon";
        }
        if(day < 7){
            return "Waxing crescent";
        }
        if(day < 8){
            return "First quarter";
        }
        if(day < 14.2){
            return "Waxing gibbous";
        }
        if(day < 15.1){
            return "Full moon";
        }
        if(day < 22){
            return "Waning gibbous";
        }
        if(day < 23){
            return "Third quarter";
        }
        if(day < 28){
            return "Waning crescent";
        }
        return "Old moon";
    }
}
