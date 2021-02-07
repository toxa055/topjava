package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MealStorageMap implements MealStorage {
    private final Map<Long, Meal> meals = new ConcurrentHashMap<>(MealsUtil.MEALS.stream()
            .collect(Collectors.toMap(Meal::getId, Function.identity())));

    @Override
    public void save(Meal meal) {
        meals.put(meal.getId(), meal);
    }

    @Override
    public void update(Meal meal) {
        meals.put(meal.getId(), meal);
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
