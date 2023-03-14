package com.example.practicaps.utils;

import java.io.Serializable;

public class Information implements Serializable {
    private String info, date;
    int hour, minute;

    public Information() {
    }

    public Information(String info, int hour, int minute) {
        this.info = info;
        this.hour = hour;
        this.minute = minute;
    }


    public Information(String date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Informacion{" +
                ", info='" + info + '\'' +
                ", hora=" + hour +
                ", min=" + minute +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
