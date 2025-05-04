package com.pillowsuite.util;

import java.io.InputStream;
import java.util.logging.*;

public class OptimusLogger {
    private static boolean isConfigured = false;

    private OptimusLogger(){}

    public static Logger getLogger(Class<?> clazz){
        if(!isConfigured){
            configure();
        }
        return Logger.getLogger(clazz.getName());
    }

    private static void configure(){
        try{
            InputStream stream = OptimusLogger.class.getClassLoader().getResourceAsStream("logging.properties");

            if(stream ==  null){
                throw new RuntimeException("logging.properties not found");
            }

            LogManager.getLogManager().readConfiguration(stream);
            isConfigured = true;

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void logInfo(Class<?> clazz, String message){
        Logger logger = getLogger(clazz);
        logger.info(message);
    }
}
