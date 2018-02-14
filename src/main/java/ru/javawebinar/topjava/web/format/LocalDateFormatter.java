package ru.javawebinar.topjava.web.format;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class LocalDateFormatter implements Formatter<LocalDate>{
    private String pattern;

    public LocalDateFormatter(String pattern) {
        this.pattern = pattern;
    }

    public String print(LocalDate date, Locale locale) {
        return getDateFormat(locale).format(date);
    }

    public LocalDate parse(String formatted, Locale locale) throws ParseException {
        if (formatted.length() == 0) {
            return null;
        }
        return LocalDate.parse(formatted, getDateFormat(locale));
    }

    private DateTimeFormatter getDateFormat(Locale locale) {
        return DateTimeFormatter.ofPattern(this.pattern, locale);
    }
}
