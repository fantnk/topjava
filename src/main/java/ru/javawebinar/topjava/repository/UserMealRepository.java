package ru.javawebinar.topjava.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;

@Repository
public interface UserMealRepository {
    UserMeal save(UserMeal userMeal);

    boolean delete(int id);

    UserMeal get(int id);

    Collection<UserMeal> getAll();
}
