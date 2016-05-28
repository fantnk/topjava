package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        /*Map<LocalDate, Integer> caloriesUsed = new TreeMap<>();

        for (UserMeal userMeal : mealList) {
//            Нужно ли?
            *//*if (!TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                break;*//*

            LocalDate ld = userMeal.getDateTime().toLocalDate();
            if (caloriesUsed.containsKey(ld))
                caloriesUsed.put(ld, caloriesUsed.get(ld) + userMeal.getCalories());
            else
                caloriesUsed.put(ld, userMeal.getCalories());
        }*/

        Map<LocalDate, Integer> caloriesUsed =
                mealList
                        .stream()
                        .collect(Collectors.groupingBy((userMeal) -> userMeal.getDateTime().toLocalDate(), HashMap::new,
                                Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExceed> result = mealList
                .stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(),
                        userMeal.getCalories(), caloriesUsed.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());

        /*result
                .stream()
                .forEach(System.out::println);*/

        return result;
    }
}
