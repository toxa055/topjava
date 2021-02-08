package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    Meal create(Meal meal, long id);

    Meal update(Meal meal);

    Meal get(long id);

    void delete(long id);

    List<Meal> getAll();
}
