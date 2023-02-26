package org.integratedmodelling.klab.api.lang.kim.impl;

import org.integratedmodelling.klab.api.lang.kim.KKimDate;

public class KimDate implements KKimDate {

    private static final long serialVersionUID = 7509585197877357307L;
    
    private int ms;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int sec;
    private boolean valid;

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public int getMonth() {
        return this.month;
    }

    @Override
    public int getDay() {
        return this.day;
    }

    @Override
    public int getHour() {
        return this.hour;
    }

    @Override
    public int getMin() {
        return this.min;
    }

    @Override
    public int getSec() {
        return this.sec;
    }

    @Override
    public int getMs() {
        return this.ms;
    }

    @Override
    public boolean isValid() {
        return this.valid;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}
