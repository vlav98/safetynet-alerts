package com.oc.safetynetalerts.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CalculateAgeUtilTests {

    String birthDate;

    @BeforeEach
    public void setUp() {
         birthDate = "01/01/2000";
    }

    @Test
    public void calculateAgeWhenBirthdateIsAfterTodayTest() {
        // GIVEN
        LocalDate now = LocalDate.of(1999, 1, 1);

        // WHEN
        int age = CalculateAgeUtil.calculateAge(birthDate, now);

        // THEN
        assertEquals(-1, age);
    }

    @Test
    public void calculateAgeWhenBirthdateIsBeforeTodayTest() {
        // GIVEN
        LocalDate now = LocalDate.of(2025, 1, 1);

        // WHEN
        int age = CalculateAgeUtil.calculateAge(birthDate, now);

        // THEN
        assertEquals(25, age);
    }


    @Test
    public void calculateAgeWhenBirthdateIsTheSameDay() {
        // GIVEN
        LocalDate now = LocalDate.of(2000, 1, 1);

        // WHEN
        int age = CalculateAgeUtil.calculateAge(birthDate, now);

        // THEN
        assertEquals(-1, age);
    }
}
