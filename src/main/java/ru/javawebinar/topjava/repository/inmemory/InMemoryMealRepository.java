package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.adminMeals.forEach(meal -> save(meal, 1));
        MealsUtil.userMeals.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> currentUserMealsMap = repository.get(userId);
        if (meal.isNew()) {
            log.info("save {}", meal);
            meal.setId(counter.incrementAndGet());
            if (currentUserMealsMap == null) {
                currentUserMealsMap = new ConcurrentHashMap<>();
                repository.put(userId, currentUserMealsMap);
            }
            return currentUserMealsMap.put(meal.getId(), meal);
        }
        // handle case: update, but not present in storage
        log.info("update {}", meal);
        return currentUserMealsMap != null
                ? currentUserMealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal)
                : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        Map<Integer, Meal> currentUserMealsMap = repository.get(userId);
        return (currentUserMealsMap != null) && (currentUserMealsMap.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Map<Integer, Meal> currentUserMealsMap = repository.get(userId);
        return currentUserMealsMap != null ? currentUserMealsMap.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return getAllFilteredAndSorted(userId, m -> true);
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAllFiltered");
        return getAllFilteredAndSorted(userId, m -> DateTimeUtil.isBetweenDates(m.getDate(), startDate, endDate));
    }

    private List<Meal> getAllFilteredAndSorted(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> currentUserMealsMap = repository.get(userId);
        return currentUserMealsMap == null
                ? new ArrayList<>()
                : repository.get(userId).values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}
