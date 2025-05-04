package com.pillowsuite.shared.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerBasic {

    private String locale;

    @JsonProperty("ticker")
    private String symbol;

    public String getSymbol(){
        return this.symbol;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public String getLocale(){
        return locale;
    }

    public void setLocale(String locale){
        this.locale = locale;
    }
}
