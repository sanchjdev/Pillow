package com.pillowsuite.service.requests;

import com.pillowsuite.service.PolygonClient;

import java.io.IOException;

// Interface class used by all different GET request classes to Polygon
public interface Request<T> {

    PolygonClient client = new PolygonClient();

    default T fetchData() throws IOException, InterruptedException {
        System.out.println("Override method to use.");
        return (T) null;
    }

   default T fetchData(String date) throws IOException, InterruptedException{
        System.out.println("Override method to use.");
        return (T) null;
    }

    default T fetchData(String symbol, String date) throws IOException, InterruptedException{
        System.out.println("Override method to use.");
        return (T) null;
    }
}
