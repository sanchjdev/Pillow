package com.pillowsuite.jobs;

import com.pillowsuite.controller.ConsumerController;

// Consumes data from rabbit queues and writes to pillow db
public class ConsumeAndWriteToDb {
    public static void main(String[] args) throws Exception {
        ConsumerController controller = new ConsumerController();
        controller.runConsumerService();
    }
}
