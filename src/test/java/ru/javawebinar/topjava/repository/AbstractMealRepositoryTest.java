package ru.javawebinar.topjava.repository;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringRunner.class)
public abstract class AbstractMealRepositoryTest {
    private final MealRepository mealRepository;

    public AbstractMealRepositoryTest(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }
}
