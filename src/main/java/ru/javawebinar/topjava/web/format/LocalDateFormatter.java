package ru.javawebinar.topjava.web.format;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class LocalDateFormatter implements Formatter<LocalDate>{
    public String print(LocalDate date, Locale locale) {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public LocalDate parse(String formatted, Locale locale) throws ParseException {
        return DateTimeUtil.parseLocalDate(formatted);
    }
}
