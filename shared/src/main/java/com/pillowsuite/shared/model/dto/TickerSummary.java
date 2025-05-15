package com.pillowsuite.shared.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TickerSummary {

    private String afterHours;
    private String close;
    private String high;
    private String low;
    private String open;
    private String preMarket;
    private String status;
    private String symbol;
    private String volume;

    @JsonProperty("from")
    private String date;

    public String getAfterHours() {
        return afterHours;
    }

    public void setAfterHours(String afterhours) {
        this.afterHours = afterhours;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getPreMarket() {
        return preMarket;
    }

    public void setPreMarket(String preMarket) {
        this.preMarket = preMarket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
