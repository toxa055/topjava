package ru.javawebinar.topjava.repository.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.repository.AbstractUserRepositoryTest;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaUserRepositoryTest extends AbstractUserRepositoryTest {

    public JpaUserRepositoryTest() {
        super(new JpaUserRepository());
    }
}