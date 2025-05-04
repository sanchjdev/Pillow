package com.pillowsuite.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketHoliday {
    private String name;
    private String date;
    private String status;

    public String getName(){ return name; }

    public void setName(){this.name = name; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) {this.status = status; }
}
