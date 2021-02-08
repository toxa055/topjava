package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MapMealStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        boolean notExist = id.isEmpty();
        Meal meal = new Meal(notExist ? 0 : Long.parseLong(id),
                LocalDateTime.parse(request.getParameter("dateTime")), request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (notExist) {
            log.debug("add new meal");
            storage.create(meal);
        } else {
            log.debug("update meal with id {}", meal.getId());
            storage.update(meal);
        }
        log.debug("redirect to meals");
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("redirect to meals");
            request.setAttribute("meals", MealsUtil.filteredByStreams(storage.getAll(),
                    LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY_LIMIT));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal = null;
        String id = request.getParameter("id");
        switch (action) {
            case "delete":
                log.debug("delete meal and redirect to meals");
                storage.delete(Long.parseLong(id));
                response.sendRedirect("meals");
                return;
            case "update":
                log.debug("choose update meal with id {}", id);
                meal = storage.get(Long.parseLong(id));
                break;
            case "add":
                log.debug("choose add new meal");
                break;
            default:
                log.debug("redirect to meals");
                response.sendRedirect("meals");
                return;
        }
        request.setAttribute("meals", meal);
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }
}
