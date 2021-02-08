package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdUtil {
    private final static AtomicLong count = new AtomicLong(0);

    public static long getId() {
        return count.incrementAndGet();
    }
}
