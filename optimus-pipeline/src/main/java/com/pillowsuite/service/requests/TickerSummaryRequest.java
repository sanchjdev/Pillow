package com.pillowsuite.service.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillowsuite.util.MarketPath;
import com.pillowsuite.shared.model.TickerSummary;

import java.io.IOException;
import java.net.http.HttpResponse;

public class TickerSummaryRequest implements Request<TickerSummary> {

    @Override
    public TickerSummary fetchData(String date, String symbol) throws IOException, InterruptedException {
        String path = MarketPath.dailyTickerSummary(symbol, date);
        HttpResponse<String> response = client.makeGetRequest(path);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), TickerSummary.class);
    }
}
