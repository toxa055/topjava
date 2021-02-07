package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    void save(Meal meal);

    void update(Meal meal);

    Meal get(long id);

    void delete(long id);

    List<Meal> getAll();
}
