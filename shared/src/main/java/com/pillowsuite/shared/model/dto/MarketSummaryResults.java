package com.pillowsuite.shared.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketSummaryResults {

    @JsonProperty("T")
    private String ticker;

    @JsonProperty("c")
    private String close;

    @JsonProperty("h")
    private String high;

    @JsonProperty("l")
    private String low;

    @JsonProperty("n")
    private String transactions;

    @JsonProperty("o")
    private String open;

    @JsonProperty("t")
    private String msTimestamp;

    @JsonProperty("v")
    private String volume;

    @JsonProperty("vw")
    private String vwap;

    private String weekDay;


    public String getTicker(){
        return ticker;
    }

    public void setTicker(String symbol){
        ticker = symbol;
    }

    public String getClose(){
        return close;
    }

    public void setClose(String c){
        close = c;
    }

    public String getHigh(){
        return high;
    }

    public void setHigh(String h){
        high = h;
    }

    public String getLow(){
        return low;
    }

    public void setLow(String l){
        low = l;
    }

    public String getTransactions(){
        return transactions;
    }

    public void setTransactions(String n){
        transactions = n;
    }

    public String getOpen(){
        return open;
    }

    public void setOpen(String o){
        open = o;
    }

    public String getMsTimestamp(){
        return msTimestamp;
    }

    public void setMsTimestamp(String t){
        msTimestamp = t;
    }

    public String getVolume(){
        return volume;
    }

    public void setVolume(String v){
        volume = v;
    }

    public String getVwap(){
        return vwap;
    }

    public void setVwap(String vw){
        vwap = vw;
    }

    public String getWeekDay() { return weekDay; }

    public void setWeekDay(String weekDay) { this.weekDay = weekDay; }
}
