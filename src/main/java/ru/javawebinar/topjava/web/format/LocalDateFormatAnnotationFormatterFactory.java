package ru.javawebinar.topjava.web.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.*;

public class LocalDateFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalDateParsing> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(Collections.singletonList(LocalDate.class));
    }

    @Override
    public Printer<LocalDate> getPrinter(LocalDateParsing annotation, Class<?> fieldType) {
        return getDateFormat(annotation, fieldType);
    }

    @Override
    public Parser<LocalDate> getParser(LocalDateParsing annotation, Class<?> fieldType) {
        return getDateFormat(annotation, fieldType);
    }

    private Formatter<LocalDate> getDateFormat(LocalDateParsing annotation, Class<?> fieldType) {
        return new LocalDateFormatter();
    }
}
