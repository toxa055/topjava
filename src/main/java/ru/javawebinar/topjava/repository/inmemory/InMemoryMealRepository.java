package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            if (meal.getUserId() == userId) {
                repository.put(meal.getId(), meal);
                log.info("save {}", meal);
                return meal;
            }
            return null;
        }
        // handle case: update, but not present in storage
        log.info("update {}", meal);
        return meal.getUserId() == userId ?
                repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) :
                null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        Meal meal = repository.get(id);
        return ((meal != null) && (meal.getUserId() == userId))
                && (repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Meal meal = repository.get(id);
        return ((meal != null) && (meal.getId() == userId)) ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll");
        return repository.values().stream()
                .filter(m -> m.getUserId() == userId)
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId) {
        log.info("getAllFiltered");
        //TODO
        return new ArrayList<>();
    }
}
