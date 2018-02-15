package ru.javawebinar.topjava.web.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LocalTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalTimeParsing> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(Collections.singletonList(LocalTime.class));
    }

    @Override
    public Printer<LocalTime> getPrinter(LocalTimeParsing annotation, Class<?> fieldType) {
        return getDateFormat(annotation, fieldType);
    }

    @Override
    public Parser<LocalTime> getParser(LocalTimeParsing annotation, Class<?> fieldType) {
        return getDateFormat(annotation, fieldType);
    }

    private Formatter<LocalTime> getDateFormat(LocalTimeParsing annotation, Class<?> fieldType) {
        return new LocalTimeFormatter();
    }
}