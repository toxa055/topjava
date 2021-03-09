package ru.javawebinar.topjava.repository.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.repository.AbstractMealRepositoryTest;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaMealRepositoryTest extends AbstractMealRepositoryTest {

    public JpaMealRepositoryTest() {
        super(new JpaMealRepository());
    }
}