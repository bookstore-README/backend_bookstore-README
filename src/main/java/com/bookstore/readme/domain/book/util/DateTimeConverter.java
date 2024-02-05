package com.bookstore.readme.domain.book.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    public static LocalDateTime converter(String strDate) {
        LocalDate localDate = LocalDate.parse(strDate, DateTimeFormatter.ISO_DATE);
        return localDate.atStartOfDay();
    }
}
