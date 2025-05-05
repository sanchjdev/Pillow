package com.pillowsuite.service.requests;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.pillowsuite.util.MarketPath;
import com.pillowsuite.shared.model.FullMover;

public class MoverRequest implements Request<List<FullMover>>{

    @Override
    public List<FullMover> fetchData() throws IOException, InterruptedException {

        List<FullMover> movers = new ArrayList<>();

        String gainerPath = MarketPath.topGainers();
        String loserPath = MarketPath.topLosers();

        HttpResponse<String> gainerResponse = client.makeGetRequest(gainerPath);
        HttpResponse<String> loserResponse = client.makeGetRequest(loserPath);

        ObjectMapper mapper = new ObjectMapper();
        FullMover fmGain = mapper.readValue(gainerResponse.body(), FullMover.class);
        FullMover fmLose = mapper.readValue(loserResponse.body(), FullMover.class);

        fmGain.setDirection("gainer");
        fmLose.setDirection("loser");

        movers.add(fmGain);
        movers.add(fmLose);

        return movers;
    }

}
