package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(USER_MEAL_1.getId(), USER_ID);
        assertMatch(meal, USER_MEAL_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getByAnotherUser() {
        assertThrows(NotFoundException.class, () -> mealService.get(USER_MEAL_5.getId(), ADMIN_ID));
    }

    @Test
    public void delete() {
        int id = USER_MEAL_1.getId();
        mealService.delete(id, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(id, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deletedByAnotherUser() {
        assertThrows(NotFoundException.class, () -> mealService.delete(ADMIN_MEAL_1.getId(), USER_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                mealService.create(new Meal(LocalDateTime.of(
                        2020, Month.JANUARY, 31, 20, 0), "Дубликат", 410),
                        USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = mealService.getBetweenInclusive(
                LocalDate.of(2021, Month.JANUARY, 30),
                LocalDate.of(2021, Month.JANUARY, 31),
                ADMIN_ID);
        assertMatch(all, ADMIN_MEAL_8, ADMIN_MEAL_7, ADMIN_MEAL_6, ADMIN_MEAL_5, ADMIN_MEAL_4, ADMIN_MEAL_3, ADMIN_MEAL_2);
    }

    @Test
    public void getWithoutStartDateInclusive() {
        List<Meal> all = mealService.getBetweenInclusive(
                null,
                LocalDate.of(2021, Month.JANUARY, 30),
                ADMIN_ID);
        assertMatch(all, ADMIN_MEAL_4, ADMIN_MEAL_3, ADMIN_MEAL_2, ADMIN_MEAL_1);
    }

    @Test
    public void getWithoutEndDateInclusive() {
        List<Meal> all = mealService.getBetweenInclusive(
                LocalDate.of(2021, Month.JANUARY, 31),
                null,
                ADMIN_ID);
        assertMatch(all, ADMIN_MEAL_9, ADMIN_MEAL_8, ADMIN_MEAL_7, ADMIN_MEAL_6, ADMIN_MEAL_5);
    }

    @Test
    public void getAll() {
        List<Meal> all = mealService.getAll(USER_ID);
        assertMatch(all, USER_MEAL_7, USER_MEAL_6, USER_MEAL_5, USER_MEAL_4, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(updated.getId(), USER_ID), getUpdated());
    }

    @Test
    public void updateByAnotherUser() {
        assertThrows(NotFoundException.class, () -> mealService.update(getUpdated(), ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = mealService.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(mealService.get(newId, USER_ID), newMeal);
    }
}
