package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        log.info("getAll Meals by user ID " + userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        log.info("getAll Meals by user ID " + userId);
        return MealsUtil.getFilteredWithExceeded(service.getBetweenDates(startDate, endDate, userId), startTime, endTime, AuthorizedUser.getCaloriesPerDay());
    }

    public Meal save(Meal meal) {
        int userId = AuthorizedUser.id();
        log.info("save meal {} by user {} ", meal, userId);
        return service.save(meal, userId);
    }

    public Meal update(Meal meal) {
        int userId = AuthorizedUser.id();
        log.info("update meal {} by user {} ", meal, userId);
        return service.update(meal, userId);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        log.info("delete meal {} by user {} ", id, userId);
        service.delete(id, userId);
    }

    public Meal get(int id) {
        int userId = AuthorizedUser.id();
        log.info("get meal {} by user {} ", id, userId);
        return service.get(id, userId);
    }
}