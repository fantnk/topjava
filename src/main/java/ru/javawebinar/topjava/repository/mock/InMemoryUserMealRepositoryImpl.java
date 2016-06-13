package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.annotation.PostConstruct;
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
        usmr.init();
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
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public UserMeal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll() {
        List<UserMeal> mealList = new LinkedList<>(repository.values());
        Collections.sort(mealList, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));

        return mealList;
    }
}

