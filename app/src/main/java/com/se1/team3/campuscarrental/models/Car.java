package com.se1.team3.campuscarrental.models;

public class Car {
    private int id;
    private String carName;
    private int capacity;
    private int image;
    private double weekday;
    private double weekend;
    private double week;
    private double gps;
    private double onStar;
    private double siriusXM;

    public Car(int id, String carName, int capacity, int image, double weekday, double weekend, double week, double gps, double onStar, double siriusXM) {
        this.id = id;
        this.carName = carName;
        this.capacity = capacity;
        this.image = image;
        this.weekday = weekday;
        this.weekend = weekend;
        this.week = week;
        this.gps = gps;
        this.onStar = onStar;
        this.siriusXM = siriusXM;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getWeekday() {
        return weekday;
    }

    public void setWeekday(double weekday) {
        this.weekday = weekday;
    }

    public double getWeekend() {
        return weekend;
    }

    public void setWeekend(double weekend) {
        this.weekend = weekend;
    }

    public double getWeek() {
        return week;
    }

    public void setWeek(double week) {
        this.week = week;
    }

    public double getGps() {
        return gps;
    }

    public void setGps(double gps) {
        this.gps = gps;
    }

    public double getOnStar() {
        return onStar;
    }

    public void setOnStar(double onStar) {
        this.onStar = onStar;
    }

    public double getSiriusXM() {
        return siriusXM;
    }

    public void setSiriusXM(double siriusXM) {
        this.siriusXM = siriusXM;
    }
}
