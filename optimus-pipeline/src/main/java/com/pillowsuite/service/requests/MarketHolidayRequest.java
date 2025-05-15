package com.pillowsuite.service.requests;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillowsuite.util.MarketPath;
import com.pillowsuite.shared.model.dto.MarketHoliday;


public class MarketHolidayRequest implements Request<List<MarketHoliday>>{

    @Override
    public List<MarketHoliday> fetchData() throws IOException, InterruptedException {
        String path = MarketPath.marketHoliday();
        HttpResponse<String> response = client.makeGetRequest(path);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response.body(), new TypeReference<List<MarketHoliday>>(){});
    }
}
