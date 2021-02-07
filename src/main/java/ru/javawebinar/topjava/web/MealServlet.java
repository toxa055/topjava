package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.MealStorageList;
import ru.javawebinar.topjava.storage.MealStorageMap;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    //    private final MealStorage storage = new MealStorageList();
    private final MealStorage storage = new MealStorageMap();
    private static final Logger log = getLogger(MealServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        boolean notExist = id.equals("");
        Meal meal = notExist ? new Meal() : storage.get(Long.parseLong(id));
        meal.setDateTime(TimeUtil.parse(request.getParameter("dateTime")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        if (notExist) {
            log.debug("add meal");
            storage.save(meal);
        } else {
            log.debug("update meal");
            storage.update(meal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        Meal meal = null;
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.filteredByStreams(storage.getAll(),
                    LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY_LIMIT));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete":
                log.debug("delete meal");
                storage.delete(Long.parseLong(request.getParameter("id")));
                response.sendRedirect("meals");
                return;
            case "update":
                meal = storage.get(Long.parseLong(request.getParameter("id")));
                break;
            case "add":
                break;
        }
        request.setAttribute("meals", meal);
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }
}
