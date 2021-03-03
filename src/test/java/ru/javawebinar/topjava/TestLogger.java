package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import ru.javawebinar.topjava.service.MealServiceTest;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TestLogger implements TestRule {

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                LocalTime startTime = LocalTime.now();
                statement.evaluate();
                LocalTime endTime = LocalTime.now();
                MealServiceTest.log.debug("Time of passing " + description + " is " +
                        ChronoUnit.MILLIS.between(startTime, endTime) + " milliseconds");
            }
        };
    }
}
