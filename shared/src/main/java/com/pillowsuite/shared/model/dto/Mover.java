package com.pillowsuite.shared.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Mover {

    private String ticker;
    private String todaysChangePerc;
    private String todaysChange;
    private String date = String.valueOf(LocalDate.now());
    private String direction;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTodaysChangePerc() {
        return todaysChangePerc;
    }

    public void setTodaysChangePerc(String todaysChangePerc) {
        this.todaysChangePerc = todaysChangePerc;
    }

    public String getTodaysChange() {
        return todaysChange;
    }

    public void setTodaysChange(String todaysChange) {
        this.todaysChange = todaysChange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDirection() { return direction; }

    public void setDirection(String direction) { this.direction = direction; }
}
