package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.mock.InMemoryUserMealRepositoryImpl;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
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
    public List<UserMeal> getAll() {
        List<UserMeal> mealList = repository.getAll();

        Collections.sort(mealList, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));

        return mealList;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        if (repository == null)
            return new ArrayList<>();

        List<UserMeal> mealList =
                repository.getAll()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toList());

        return mealList;
    }

    @Override
    public List<UserMeal> getFilteredByTime(LocalTime startTime, LocalTime endTime) {
        return repository.getAll().stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserMeal> getFilteredByDate(LocalDate startDate, LocalDate endDate) {
        return repository.getAll().stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalDate(), startDate, endDate))
                .collect(Collectors.toList());
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
