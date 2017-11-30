package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger log = LoggerFactory.getLogger(MealServiceTest.class);

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_ID+2, USER_ID);
        assertMatch(MEAL_USER, meal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_USER.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_USER2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(1, USER_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> between = service.getBetweenDates(LocalDate.of(2015, Month.JANUARY, 1), LocalDate.of(2015, Month.JANUARY, 30), USER_ID);
        assertMatch(between, MEAL_USER);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL_USER2, MEAL_USER);
    }

    @Test
    public void update() throws Exception {
        Meal update = new Meal(MEAL_USER);
        update.setDescription("update_test");
        update.setCalories(333);
        service.update(update, USER_ID);
        assertMatch(service.get(update.getId(), USER_ID), update);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        Meal update = new Meal(MEAL_USER);
        service.update(update, 1);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2018, Month.MAY, 30, 10, 0),"newCreate", 800);
        Meal createMeal = service.create(newMeal, USER_ID);
        assertMatch(service.getAll(USER_ID), createMeal, MEAL_USER2, MEAL_USER);
    }

}