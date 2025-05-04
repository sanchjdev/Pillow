package com.pillowsuite.shared.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AllTickers {

    @JsonProperty("results")
    private List<TickerBasic> symbols;

    @JsonProperty("next_url")
    private String next = null;

    public List<TickerBasic> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<TickerBasic> symbols) {
        this.symbols = symbols;
    }

    public String getNext(){
        return next;
    }

    public void setNext(String next){
        this.next = next;
    }
}
