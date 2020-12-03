package com.everis.exchange.common;

import java.time.LocalDate;
import java.util.Random;

public class Utils {
    private Utils() {}

    public static Integer randomNumbers(Integer min, Integer max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    public static LocalDate createEndDate(LocalDate endDate) {
        return endDate.plusDays(1);
    }
}
