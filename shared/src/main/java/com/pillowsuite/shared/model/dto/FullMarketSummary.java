package com.pillowsuite.shared.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FullMarketSummary {

    @JsonProperty("results")
    private List<MarketSummaryResults> summaries;

    private String summaryDate;

    private int resultsCount;

    public List<MarketSummaryResults> getSummaries(){
        return summaries;
    }

    public void setSummaries(List<MarketSummaryResults> summaries){
        this.summaries = summaries;
    }

    public int getResultsCount(){
        return resultsCount;
    }

    public void setResultsCount(int count){
        resultsCount = count;
    }

    public String getSummaryDate() {
        return summaryDate;
    }

    public void setSummaryDate(String summaryDate) {
        this.summaryDate = summaryDate;
    }




}
