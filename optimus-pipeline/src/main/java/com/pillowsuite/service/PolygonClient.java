package com.pillowsuite.service;

import com.pillowsuite.util.MarketPath;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class PolygonClient {

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private final String baseUrl = MarketPath.getBaseUrl();
    private final String apiKey = MarketPath.getApiKey();


    public HttpResponse<String> makeGetRequest(String path) throws IOException, InterruptedException{
        HttpRequest request = buildRequest(path);

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> makeNextRequest(String nextUrl) throws IOException, InterruptedException{
        String endpoint = nextUrl + "&apiKey=" + apiKey;

        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(endpoint))
                                .GET()
                                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }


    private HttpRequest buildRequest(String path){
        String endpoint = baseUrl + path + apiKey;

        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();
    }

}
