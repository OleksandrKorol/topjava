package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends MealRestController {

    @Autowired
    private MealService mealService;

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String getAll(HttpServletRequest request) {
        request.setAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("create")
    public String createMeal(HttpServletRequest request) {
        log.info("Get create");
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        request.setAttribute("updateOrEdit", "create");
        return "mealForm";
    }

    @PostMapping("meals")
    public String doPost(HttpServletRequest request) throws UnsupportedEncodingException {
//        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                super.create(meal);
            } else {
                super.update(meal, getId(request));
            }
            return "redirect:meals";

        } else if ("filter".equals(action)) {
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
            request.setAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
            return "meals";
        }
        return "redirect:meals";
    }

    @GetMapping("update")
    public String updateMeal(HttpServletRequest request) {
        final Meal meal = super.get(getId(request));
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("delete")
    public String deleteMeal(HttpServletRequest request) {
        int id = getId(request);
        super.delete(id);
        return "redirect:meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }



}

