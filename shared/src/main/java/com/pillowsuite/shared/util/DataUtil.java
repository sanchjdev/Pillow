package com.pillowsuite.shared.util;

import com.pillowsuite.shared.model.repository.JdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataUtil {

    protected static final Logger logger = LoggerFactory.getLogger(JdbcRepository.class);

    public static java.sql.Date toSqlDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            java.util.Date utilDate = formatter.parse(date);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException pe){
            logger.error(pe.getMessage());
        }
        return null;
    }

    public static float toFloat3(String num){
        float value = Float.parseFloat(num);
        return Math.round(value * 1000) / 1000f;
    }

    public static float toFloat2(String num){
        if(num != null){
            float value = Float.parseFloat(num);
            return Math.round(value * 100) / 100f;
        }
        return 0;
    }

    public static boolean isScientific(String str) {
        return str.matches("[-+]?\\d*\\.?\\d+[eE][-+]?\\d+");
    }

    public static int decimalToInt(String number){
        if(number != null){
            double dbl = Double.parseDouble(number);
            return (int) dbl;
        }
        return 0;
    }
}
