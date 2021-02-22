package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 100;

    public static final Meal USER_MEAL_1 = new Meal(START_SEQ + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_2 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL_4 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal USER_MEAL_5 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal USER_MEAL_6 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal USER_MEAL_7 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static final Meal ADMIN_MEAL_1 = new Meal(START_SEQ + 9, LocalDateTime.of(2021, Month.JANUARY, 29, 10, 0), "Завтрак", 300);
    public static final Meal ADMIN_MEAL_2 = new Meal(START_SEQ + 10, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal ADMIN_MEAL_3 = new Meal(START_SEQ + 11, LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal ADMIN_MEAL_4 = new Meal(START_SEQ + 12, LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal ADMIN_MEAL_5 = new Meal(START_SEQ + 13, LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal ADMIN_MEAL_6 = new Meal(START_SEQ + 14, LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal ADMIN_MEAL_7 = new Meal(START_SEQ + 15, LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal ADMIN_MEAL_8 = new Meal(START_SEQ + 16, LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal ADMIN_MEAL_9 = new Meal(START_SEQ + 17, LocalDateTime.of(2021, Month.FEBRUARY, 1, 20, 0), "Ужин", 400);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.JANUARY, 25, 11, 30), "Новая еда", 550);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 15, 12, 45));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(660);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
