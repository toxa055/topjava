package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealStorageList implements MealStorage {
    private final List<Meal> meals = new CopyOnWriteArrayList<>(MealsUtil.MEALS);

    @Override
    public void save(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void update(Meal meal) {
        meals.set(getIndex(meal.getId()), meal);
    }

    @Override
    public Meal get(long id) {
        return meals.get(getIndex(id));
    }

    @Override
    public void delete(long id) {
        meals.remove(getIndex(id));
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    private int getIndex(long id) {
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId() == id) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }
}
