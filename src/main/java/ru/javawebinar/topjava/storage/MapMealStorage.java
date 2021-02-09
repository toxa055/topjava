package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealStorage implements MealStorage {
    private final AtomicInteger count = new AtomicInteger(0);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(this::create);
    }

    @Override
    public Meal create(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(count.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public Meal update(Meal meal) {
        return meals.replace(meal.getId(), meal) == null ? null : meal;
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
