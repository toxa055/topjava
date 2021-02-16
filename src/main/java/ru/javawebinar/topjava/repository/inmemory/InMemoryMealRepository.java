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
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(1, new ConcurrentHashMap<>());
        repository.put(2, new ConcurrentHashMap<>());
        MealsUtil.adminMeals.forEach(meal -> save(meal, 1));
        MealsUtil.userMeals.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(), meal);
            log.info("save {}", meal);
            return meal;
        }
        // handle case: update, but not present in storage
        log.info("update {}", meal);
        Meal updatedMeal = get(meal.getId(), userId);
        return (updatedMeal != null) && updatedMeal.getId().equals(meal.getId())
                ? repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal)
                : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        Meal meal = get(id, userId);
        return (meal != null) && (meal.getId() == id)
                && (repository.get(userId).remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Meal meal = repository.get(userId).get(id);
        return ((meal != null) && (meal.getId() == id)) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAllFiltered");
        return (startDate == null) && (endDate == null)
                ? getAll(userId)
                : getAll(userId).stream()
                .filter(m -> DateTimeUtil.isBetweenDates(m.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
