package com.pillowsuite.integration.marketdata;

import java.text.MessageFormat;

import com.pillowsuite.shared.util.PropertiesLoader;


public class MarketPath {

    private static final PropertiesLoader config = new PropertiesLoader("config.properties");
    private static final String base = config.get("market-api.url");
    private static final String key = config.get("market-api.key");

    public static String allTickers(){
        return config.get("market-api.endpoint.all-tickers");
    }

    // symbol - ticker, date YYYY-MM-DD
    public static String tickerOverview(String symbol, String date){
        return MessageFormat.format(config.get("market-api.endpoint.tickerOverview"), symbol, date);
    }

    public static String dailyTickerSummary(String symbol, String date){
        return MessageFormat.format(config.get("market-api.endpoint.daily-ticker-summary"), symbol, date);
    }

    public static String dailyMarketSummary(String date){
        return MessageFormat.format(config.get("market-api.endpoint.daily-market-summary"), date);
    }

    public static String topGainers(){
        return (config.get("market-api.endpoint.top-gainers"));
    }

    public static String topLosers(){
        return (config.get("market-api.endpoint.top-losers"));
    }

    public static String marketHoliday(){return (config.get("market-api.endpoint.market-holiday"));}

    public static String getApiKey(){
        return key;
    }

    public static String getBaseUrl(){
        return base;
    }

}
