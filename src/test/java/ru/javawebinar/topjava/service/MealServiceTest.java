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
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;


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
        Meal meal = mealService.get(userMeal1.getId(), USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getByAnotherUser() {
        assertThrows(NotFoundException.class, () -> mealService.get(userMeal5.getId(), ADMIN_ID));
    }

    @Test
    public void delete() {
        int id = userMeal1.getId();
        mealService.delete(id, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(id, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deletedByAnotherUser() {
        assertThrows(NotFoundException.class, () -> mealService.delete(adminMeal1.getId(), USER_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> mealService.create(new Meal(userMeal4.getDateTime(), "Дубликат", 410), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = mealService.getBetweenInclusive(
                LocalDate.of(2021, Month.JANUARY, 30),
                LocalDate.of(2021, Month.JANUARY, 31),
                ADMIN_ID);
        assertMatch(all, adminMeal8, adminMeal7, adminMeal6, adminMeal5, adminMeal4, adminMeal3, adminMeal2);
    }

    @Test
    public void getWithoutStartDateInclusive() {
        List<Meal> all = mealService.getBetweenInclusive(
                null,
                LocalDate.of(2021, Month.JANUARY, 30),
                ADMIN_ID);
        assertMatch(all, adminMeal4, adminMeal3, adminMeal2, adminMeal1);
    }

    @Test
    public void getWithoutEndDateInclusive() {
        List<Meal> all = mealService.getBetweenInclusive(
                LocalDate.of(2021, Month.JANUARY, 31),
                null,
                ADMIN_ID);
        assertMatch(all, adminMeal9, adminMeal8, adminMeal7, adminMeal6, adminMeal5);
    }

    @Test
    public void getAll() {
        List<Meal> all = mealService.getAll(USER_ID);
        assertMatch(all, userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
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
