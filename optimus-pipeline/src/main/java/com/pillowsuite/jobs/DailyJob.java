package com.pillowsuite.jobs;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pillowsuite.controller.DataTransferController;
import com.pillowsuite.util.MarketDateUtil;


public class DailyJob {

    private static final Logger logger = LoggerFactory.getLogger(DailyJob.class);

    public static void main(String[] args) throws Exception {
        String date = args.length > 0 ? args[0] : String.valueOf(LocalDate.now());
        DataTransferController controller = new DataTransferController();
        controller.runDailyJob(date);

    }

}
