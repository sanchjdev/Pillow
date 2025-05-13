package com.pillowsuite.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pillowsuite.service.requests.MarketHolidayRequest;
import com.pillowsuite.shared.model.dto.MarketHoliday;


public class MarketDateUtil {

    private static final List<String> closed = new ArrayList<>(Arrays.asList("SATURDAY", "SUNDAY"));

    // populate the closed list with closed market dates
    static{
        try {
            populateClosedList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // date is not a closed holiday and a saturday or sunday
    public static boolean isMarketOpen(LocalDate date) {
        String dateString = date.toString();
        String day = String.valueOf(date.getDayOfWeek());
        return (!closed.contains(day) && !closed.contains(dateString));
    }

    // isMarketOpen and is the date today or before today
    public static boolean hasMarketData(LocalDate date) {
        return (isMarketOpen(date) && !date.isAfter(LocalDate.now()));
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

    public static boolean isEndOfDay(){
        LocalTime marketClose = LocalTime.of(13, 15);
        LocalTime now = LocalTime.now();

        return !now.isBefore(marketClose);
    }

    public static List<LocalDate> datesWithMarketDataInRange(LocalDate startDate, LocalDate endDate){
        LocalDate point = startDate;
        List<LocalDate> marketDates = new ArrayList<>();
        while(!point.isAfter(endDate)){
            if(hasMarketData(point)){
                marketDates.add(point);
            }
            point = point.plusDays(1);
        }
        return marketDates;
    }

//    public static List<String> datesWithMarketDataInWindow(String endDate, int window){
//
//    }
}
