package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;


@Service
public class MealService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        log.info("create {}", meal);
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(int userId) {
        log.info("getAll");
        return repository.getAll(userId);
    }

    public Collection<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAllFiltered");
        return repository.getAllFiltered(userId, startDate, endDate);
    }

    public void update(Meal meal, int userId) {
        log.info("update {} with", meal);
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}