package com.urise.webapp;

import java.util.Calendar;
import java.util.Date;

public class MainDate {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        System.out.println(date.getTime() - System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
    }
}
