package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal save(UserMeal userMeal) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    UserMeal get(int id) throws NotFoundException;

    List<UserMeal> getAll();

    List<UserMeal> getAll(int userId);

    List<UserMeal> getFilteredByTime(LocalTime startTime, LocalTime endTime);

    List<UserMeal> getFilteredByDate(LocalDate startDate, LocalDate endDate);

    UserMeal update(UserMeal userMeal) throws NotFoundException;
}
