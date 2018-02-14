package ru.javawebinar.topjava.web.format;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    private String pattern;

    public LocalTimeFormatter(String pattern) {
        this.pattern = pattern;
    }

    public String print(LocalTime date, Locale locale) {
        return getDateFormat(locale).format(date);
    }

    public LocalTime parse(String formatted, Locale locale) throws ParseException {
        if (formatted.length() == 0) {
            return null;
        }
        return LocalTime.parse(formatted, getDateFormat(locale));
    }

    private DateTimeFormatter getDateFormat(Locale locale) {
        return DateTimeFormatter.ofPattern(this.pattern, locale);
    }
}
