package com.pillowsuite.service.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillowsuite.util.MarketPath;
import com.pillowsuite.shared.model.dto.FullTickerOverview;

import java.io.IOException;
import java.net.http.HttpResponse;

// return ticker info for a specific symbol from polygon api
public class TickerOverviewRequest implements Request<FullTickerOverview>{

    @Override
    public FullTickerOverview fetchData(String symbol, String date) throws IOException, InterruptedException {
        String path = MarketPath.tickerOverview(symbol, date);
        HttpResponse<String> response = client.makeGetRequest(path);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), FullTickerOverview.class);
    }
}
