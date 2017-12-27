package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL2;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Override
    public void create() throws Exception {
        super.create();
    }

    @Override
    public void duplicateMailCreate() throws Exception {
        super.duplicateMailCreate();
    }

    @Override
    public void delete() throws Exception {
        super.delete();
    }

    @Override
    public void notFoundDelete() throws Exception {
        super.notFoundDelete();
    }

    @Override
    public void get() throws Exception {
        super.get();
    }

    @Test
    public void getWithMeals() throws Exception {
        User user = service.get(USER_ID);
        List<Meal> meals = user.getMeals();
        assertMatch(user, USER);
        MealTestData.assertMatch(meals, MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6);
    }

    @Override
    public void getNotFound() throws Exception {
        super.getNotFound();
    }

    @Override
    public void getByEmail() throws Exception {
        super.getByEmail();
    }

    @Override
    public void update() throws Exception {
        super.update();
    }

    @Override
    public void getAll() throws Exception {
        super.getAll();
    }
}
