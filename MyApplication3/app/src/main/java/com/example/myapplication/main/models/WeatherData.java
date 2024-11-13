package com.example.myapplication.main.models;

import android.content.Intent;

public class WeatherData {
    private Double hum;
    private Double light;
    private Double los;
    private Double ppm;
    private Integer rain;
    private Double rain_pred;
    private Double temp;
    private Integer weather_code;

    // Required default constructor for Firebase
    public WeatherData() {
    }

    public WeatherData(Double hum, Double light, Double los, Double ppm, int rain, Double rain_pred, Double temp, int weather_code) {
        this.hum = hum;
        this.light = light;
        this.los = los;
        this.ppm = ppm;
        this.rain = rain;
        this.rain_pred = rain_pred;
        this.temp = temp;
        this.weather_code = weather_code;
    }

    public Double getHum() {
        return hum;
    }

    public Double getLight() {
        return light;
    }

    public Double getLos() {
        return los;
    }

    public Double getPpm() {
        return ppm;
    }

    public int getRain() {
        return rain;
    }

    public Double getRain_pred() {
        return rain_pred;
    }

    public Double getTemp() {
        return temp;
    }

    public int getWeather_code() {
        return weather_code;
    }
}
