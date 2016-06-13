package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal save(UserMeal userMeal) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    UserMeal get(int id) throws NotFoundException;

    Collection<UserMeal> getAll();

    Collection<UserMeal> getAll(int userId);

    UserMeal update(UserMeal userMeal) throws NotFoundException;
}
