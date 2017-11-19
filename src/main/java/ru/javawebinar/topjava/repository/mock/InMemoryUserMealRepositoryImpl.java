package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.ADMIN_ID;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.USER_ID;

@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private static final Comparator<Meal> USER_MEAL_COMPARATOR = (um1, um2) -> um2.getDateTime().compareTo(um1.getDateTime());

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), USER_ID);

        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "АдминЛаунч", 510), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "АдминЛаунч", 510), ADMIN_ID);
    }
    @Override
    public Meal save(Meal meal, int userId) {
        Integer mealId = meal.getId();

        if (meal.isNew()) {
            mealId = counter.incrementAndGet();
            meal.setId(mealId);
        } else if (get(mealId, userId) == null) {
            return null;
        }

        Map<Integer, Meal> userMeal = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        userMeal.put(mealId, meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeal = repository.get(userId);
        return userMeal != null && userMeal.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeal = repository.get(userId);
        return userMeal == null ? null : userMeal.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.get(userId).values().stream()
                .sorted(USER_MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAll(userId).stream()
                .filter(um -> DateTimeUtil.isBetween(um.getDateTime(), startDateTime, endDateTime))
                .sorted(USER_MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }
}

