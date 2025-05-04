package com.pillowsuite.shared.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FullMover {

    @JsonProperty("tickers")
    private List<Mover> movers;

    private String direction;

    public List<Mover> getMovers() {
        return movers;
    }

    public void setMovers(List<Mover> movers) {
        this.movers = movers;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
