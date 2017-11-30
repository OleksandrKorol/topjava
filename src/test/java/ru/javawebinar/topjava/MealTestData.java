package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealTestData {
    public static final Meal MEAL_USER = new Meal(USER_ID+2, LocalDateTime.of(2015, Month.JANUARY, 14, 14, 20), "юзер1", 500);
    public static final Meal MEAL_USER2 = new Meal(USER_ID+3, LocalDateTime.of(2016, Month.FEBRUARY, 4, 15, 10), "юзер2", 510);

    public static final Meal MEAL_ADMIN = new Meal(USER_ID+4, LocalDateTime.of(2015, Month.JANUARY, 14, 15, 10), "адмін", 500);
    public static final Meal MEAL_ADMIN2 = new Meal(USER_ID+5, LocalDateTime.of(2016, Month.FEBRUARY, 14, 15, 10), "адмін2", 500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
