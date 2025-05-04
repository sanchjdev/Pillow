package com.pillowsuite.integration.marketdata.request;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillowsuite.integration.marketdata.MarketPath;
import com.pillowsuite.shared.model.MarketHoliday;


public class MarketHolidayRequest implements Request<List<MarketHoliday>>{

    @Override
    public List<MarketHoliday> fetchData() throws IOException, InterruptedException {
        String path = MarketPath.marketHoliday();
        HttpResponse<String> response = client.makeGetRequest(path);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response.body(), new TypeReference<List<MarketHoliday>>(){});
    }
}
