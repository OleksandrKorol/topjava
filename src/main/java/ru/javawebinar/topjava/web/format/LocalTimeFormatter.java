package ru.javawebinar.topjava.web.format;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    public String print(LocalTime date, Locale locale) {
        return date.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public LocalTime parse(String formatted, Locale locale) throws ParseException {
        return DateTimeUtil.parseLocalTime(formatted);
    }
}
