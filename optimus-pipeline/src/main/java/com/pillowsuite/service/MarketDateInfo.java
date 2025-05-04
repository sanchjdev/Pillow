package com.pillowsuite.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pillowsuite.integration.marketdata.request.MarketHolidayRequest;
import com.pillowsuite.shared.model.MarketHoliday;


public class MarketDateInfo {

    private static final List<String> closed = new ArrayList<>(Arrays.asList("SATURDAY", "SUNDAY"));

    // populate the closed list with closed market dates
    static{
        try {
            populateClosedList();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // date is not a closed holiday and a saturday or sunday
    public static boolean isMarketOpen(String date) {
        String day = String.valueOf(LocalDate.parse(date).getDayOfWeek());
        return (!closed.contains(day) && !closed.contains(date));
    }

    // isMarketOpen and is the date today or before today
    public static boolean hasMarketData(String date) {
        return (isMarketOpen(date) && !LocalDate.parse(date).isAfter(LocalDate.now()));
    }

    private static void populateClosedList() throws IOException, InterruptedException {
        MarketHolidayRequest request = new MarketHolidayRequest();
        List<MarketHoliday> holidays = request.fetchData();
        for(MarketHoliday holiday : holidays){
            // if the market is closed on the given date and is not yet in the list - add to closed list
            if(holiday.getStatus().equals("closed") && !closed.contains(holiday.getDate())){
                closed.add(holiday.getDate());
            }
        }
    }
}
