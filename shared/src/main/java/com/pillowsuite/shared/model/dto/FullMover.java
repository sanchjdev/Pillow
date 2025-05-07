package com.pillowsuite.shared.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FullMover {

    @JsonProperty("tickers")
    private List<Mover> movers;

    public List<Mover> getMovers() {
        return movers;
    }

    public void setMovers(List<Mover> movers) {
        this.movers = movers;
    }

}
