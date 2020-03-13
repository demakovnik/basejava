package com.urise.webapp;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class MainDate {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        System.out.println(date.getYear());
        System.out.println(date.getMonthValue());
        System.out.println(date.getDayOfMonth());
        LocalDate localDate = LocalDate.of(2020,3,30);
        localDate.getYear();
        localDate.getMonthValue();
    }
}
