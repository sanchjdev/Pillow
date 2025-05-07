package com.pillowsuite.shared.model.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerOverview {

    private String ticker;
    private String name;
    private String market;
    private String locale;
    private String type;
    private String active;

    @JsonProperty("total_employees")
    private String employees;

    @JsonProperty("weighted_shares_outstanding")
    private String weightedSharesOutstanding;

    @JsonProperty("share_class_shares_outstanding")
    private String sharesOutstanding;

    @JsonProperty("market_cap")
    private String marketCap;

    @JsonProperty("share_class_figi")
    private String shareClassFigi;

    @JsonProperty("currency_name")
    private String currency;

    @JsonProperty("primary_exchange")
    private String primaryExchange;


    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getEmployees() {
        return employees;
    }

    public void setEmployees(String employees) {
        this.employees = employees;
    }

    public String getWeightedSharesOutstanding() {
        return weightedSharesOutstanding;
    }

    public void setWeightedSharesOutstanding(String weightedSharesOutstanding) {
        this.weightedSharesOutstanding = weightedSharesOutstanding;
    }

    public String getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(String sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getShareClassFigi() {
        return shareClassFigi;
    }

    public void setShareClassFigi(String shareClassFigi) {
        this.shareClassFigi = shareClassFigi;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrimaryExchange() {
        return primaryExchange;
    }

    public void setPrimaryExchange(String primaryExchange) {
        this.primaryExchange = primaryExchange;
    }
}
