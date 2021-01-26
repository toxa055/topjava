package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 20, 21, 7), "Ужин", 300),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 20, 7, 30), "Первый завтрак", 1200),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 21, 20, 10), "Ужин2", 800),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 21, 22, 30), "Ужин3", 900),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 21, 21, 20), "Ужин1", 850),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 20, 9, 0), "Второй завтрак", 1100)
        );

        System.out.println("eveningMealsByCycles:");
        List<UserMealWithExcess> eveningMeals = filteredByCycles(meals, LocalTime.of(17, 0), LocalTime.of(22, 0), 2100);
        eveningMeals.forEach(System.out::println);

        System.out.println("eveningMealsByStream:");
        List<UserMealWithExcess> eveningMealsByStream = filteredByStreams(meals, LocalTime.of(17, 0), LocalTime.of(22, 0), 2100);
        eveningMealsByStream.forEach(System.out::println);

        System.out.println("morningMealsByCycles:");
        List<UserMealWithExcess> morningMeals = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        morningMeals.forEach(System.out::println);

        System.out.println("morningMealsByStream:");
        List<UserMealWithExcess> morningMealsByStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        morningMealsByStream.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        for (UserMeal userMeal : meals) {
            LocalDate mealDate = userMeal.getDateTime().toLocalDate();
            caloriesPerDayMap.put(mealDate, caloriesPerDayMap.getOrDefault(mealDate, 0) + userMeal.getCalories());
        }
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            LocalDateTime mealDateTime = userMeal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(mealDateTime.toLocalTime(), startTime, endTime)) {
                userMealWithExcessList.add(new UserMealWithExcess(mealDateTime, userMeal.getDescription(),
                        userMeal.getCalories(), caloriesPerDayMap.get(mealDateTime.toLocalDate()) > caloriesPerDay));
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = meals.stream()
                .collect(Collectors.toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
