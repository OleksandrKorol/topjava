package ru.javawebinar.topjava;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class TimeTest extends TestWatcher {
    private static final Logger log = LoggerFactory.getLogger(TimeTest.class);

    private static Map<String, Long> map = new HashMap<>();
    private LocalDateTime startDateTime;

    @Override
    protected void starting(Description description) {
        startDateTime = LocalDateTime.now();
    }

    @Override
    protected void finished(Description description) {
        LocalDateTime toDateTime = LocalDateTime.now();
        LocalDateTime tempDateTime = LocalDateTime.from(startDateTime);
        long millis = tempDateTime.until(toDateTime, ChronoUnit.MILLIS);

        map.put(description.getMethodName(), millis);
    }

    public static void after() {
        map.forEach((key, val) -> log.info("test duration: {} - {}", key, val));
    }
}
