package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapMealStorage implements MealStorage {
    private final Map<Long, Meal> meals = new ConcurrentHashMap<>(MealsUtil.meals.stream()
            .collect(Collectors.toMap(Meal::getId, Function.identity())));

    @Override
    public Meal create(Meal meal, long id) {
        if (id == 0) {
            meals.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public Meal update(Meal meal) {
        if (meals.containsKey(meal.getId())) {
            meals.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public Meal get(long id) {
        return meals.get(id);
    }

    @Override
    public void delete(long id) {
        meals.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
