package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 100;

    public static final Meal userMeal1 = new Meal(START_SEQ + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак user", 500);
    public static final Meal userMeal2 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед user", 1000);
    public static final Meal userMeal3 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин user", 500);
    public static final Meal userMeal4 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение user", 100);
    public static final Meal userMeal5 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак user", 1000);
    public static final Meal userMeal6 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед user", 500);
    public static final Meal userMeal7 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин user", 410);

    public static final Meal adminMeal1 = new Meal(START_SEQ + 9, LocalDateTime.of(2021, Month.JANUARY, 29, 10, 0), "Завтрак admin", 300);
    public static final Meal adminMeal2 = new Meal(START_SEQ + 10, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак admin", 500);
    public static final Meal adminMeal3 = new Meal(START_SEQ + 11, LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед admin", 1000);
    public static final Meal adminMeal4 = new Meal(START_SEQ + 12, LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин admin", 500);
    public static final Meal adminMeal5 = new Meal(START_SEQ + 13, LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение admin", 100);
    public static final Meal adminMeal6 = new Meal(START_SEQ + 14, LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Завтрак admin", 1000);
    public static final Meal adminMeal7 = new Meal(START_SEQ + 15, LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед admin", 500);
    public static final Meal adminMeal8 = new Meal(START_SEQ + 16, LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Ужин admin", 410);
    public static final Meal adminMeal9 = new Meal(START_SEQ + 17, LocalDateTime.of(2021, Month.FEBRUARY, 1, 20, 0), "Ужин admin", 400);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.JANUARY, 25, 11, 30), "Новая еда", 550);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
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
