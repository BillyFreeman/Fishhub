package com.victor.fishhub.service.moonphase;

public class MoonPhase {

    private int day; //day of moon cycle
    private String name; //phase name

    public MoonPhase() {
    }

    public MoonPhase(int day, String name) {
        this.day = day;
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
