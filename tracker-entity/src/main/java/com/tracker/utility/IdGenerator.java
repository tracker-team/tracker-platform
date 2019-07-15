package com.tracker.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdGenerator {
    private final static SimpleDateFormat ID_DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmssSSS");
    private final static Random rand = new Random();

    public static String getUUIDGeneratedValue() {
        Date currentTime = new Date();
        DateFormat idDateFormat = ID_DATE_FORMAT;
        long randomNumber = (long) (rand.nextInt(99999) + 100000);
        return idDateFormat.format(currentTime) + String.format("%05d", randomNumber);
    }

    public static String generateStepId(){
        return "ST"+getUUIDGeneratedValue();
    }
}
