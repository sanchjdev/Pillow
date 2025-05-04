package com.pillowsuite.integration.marketdata.request;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillowsuite.integration.marketdata.MarketPath;
import com.pillowsuite.shared.model.AllTickers;
import com.pillowsuite.shared.model.TickerBasic;

public class AllTickersRequest implements Request<List<String>>{

    // Returns a List<String> of ticker symbols supported by Polygon.io
    @Override
    public List<String> fetchData() throws IOException, InterruptedException{
        List<String> symbolsList = new ArrayList<>();
        String path = MarketPath.allTickers();
        String baseUrl = MarketPath.getBaseUrl();
        String apiKey = MarketPath.getApiKey();
        String endpoint = baseUrl + path + apiKey;
        ObjectMapper mapper = new ObjectMapper();

        while(endpoint != null){
            HttpResponse<String> response = client.makeNextRequest(endpoint);
            AllTickers allTickers = mapper.readValue(response.body(), AllTickers.class);
            List<TickerBasic> tickers = allTickers.getSymbols();
            endpoint = allTickers.getNext();

            for(TickerBasic ticker : tickers){
                if(ticker.getLocale().equals("us")){
                    symbolsList.add(ticker.getSymbol());
                }
            }
        }

        return symbolsList;
    }
}
