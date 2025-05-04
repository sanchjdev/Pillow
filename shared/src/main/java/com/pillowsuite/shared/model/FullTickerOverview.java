package com.pillowsuite.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FullTickerOverview {

    @JsonProperty("results")
    private TickerOverview ticker;

    public TickerOverview getTicker(){
        return ticker;
    }

    public void setTicker(TickerOverview ticker){
        this.ticker = ticker;
    }

}
