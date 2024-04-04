package com.oc.safetynetalerts.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Component
public class CalculateAgeUtil {

    public static int calculateAge(String birthDate, LocalDate now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate parsedBirthDate = LocalDate.parse(birthDate, formatter);
        if (now.isAfter(parsedBirthDate)) {
            return Period.between(parsedBirthDate, now).getYears();
        }
        return -1;
    }

}
