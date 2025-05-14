package com.pillowsuite.service.requests;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Comparator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pillowsuite.util.MarketPath;
import com.pillowsuite.shared.model.dto.FullMarketSummary;
import com.pillowsuite.shared.model.dto.MarketSummaryResults;



public class MarketSummaryRequest implements Request<FullMarketSummary>{

    Logger logger = LoggerFactory.getLogger(MarketSummaryRequest.class);

    @Override
    public FullMarketSummary fetchData(String date) throws IOException, InterruptedException {
        String path = MarketPath.dailyMarketSummary(date);
        HttpResponse<String> response = client.makeGetRequest(path);

        ObjectMapper mapper = new ObjectMapper();
        FullMarketSummary fms = mapper.readValue(response.body(), FullMarketSummary.class);
        try {
            fms.getSummaries().sort(Comparator.comparing(MarketSummaryResults::getTicker));
            fms.setSummaryDate(date);
        } catch(Exception e){
            logger.info("MarketSummary is empty.");
            return null;
        }

        return fms;
    }

}
