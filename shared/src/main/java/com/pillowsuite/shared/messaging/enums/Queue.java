package com.pillowsuite.shared.messaging.enums;

public enum Queue {
    ALL_TICKERS("all_tickers"),
    DAILY_MARKET_SUMMARY("daily_market_summary"),
    DAILY_TICKER_SUMMARY("daily_ticker_summary"),
    TICKER_OVERVIEW("ticker_overview"),
    TOP_MOVERS("top_movers");


    private final String name;

    Queue(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
