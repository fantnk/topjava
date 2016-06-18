package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private static Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    public InMemoryUserMealRepositoryImpl() {
    }

    public static void main(String[] args) {
        InMemoryUserMealRepositoryImpl usmr = new InMemoryUserMealRepositoryImpl();
        //usmr.init();
        UserMeal um = new UserMeal(1, LocalDateTime.now(), "des", 333, 1);
        usmr.save(um);
        System.out.println(usmr.getAll());
    }
    @Override
    public UserMeal save(UserMeal userMeal) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public UserMeal get(int id) {
        return repository.get(id);
    }

    @Override
    public List<UserMeal> getAll() {

        return new ArrayList<>(repository.values());
    }
}

