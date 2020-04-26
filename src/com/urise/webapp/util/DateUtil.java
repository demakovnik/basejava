package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/uuuu");
    public static LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String localDateToText(LocalDate localDate) {
        if (localDate.equals(NOW)) {
            return "настоящее время";
        }
        return localDate.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate stringToLocalDate(String text) {
        if (text.trim().length() == 0 || text == null || text.equals("настоящее время")) {
            return NOW;
        }
        YearMonth yearMonth = YearMonth.parse(text, DATE_TIME_FORMATTER);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }
}
