package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.mock.InMemoryUserMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private InMemoryUserMealRepositoryImpl repository;

    @Override
    public UserMeal save(UserMeal userMeal) throws NotFoundException {
        return repository.save(userMeal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        repository.delete(id);
    }

    @Override
    public UserMeal get(int id) throws NotFoundException {
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll() {
        return repository.getAll();
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        if (repository == null)
            return new ArrayList<>();

        List<UserMeal> mealList =
                repository.getAll()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toList());

        return mealList;
    }

    /*public static void main(String[] args) {
        UserMealService ums = new UserMealServiceImpl();
        ums.
        ums.getAll(1).forEach(System.out::println);
    }*/

    @Override
    public UserMeal update(UserMeal userMeal) throws NotFoundException {
        return save(userMeal);
    }
}
