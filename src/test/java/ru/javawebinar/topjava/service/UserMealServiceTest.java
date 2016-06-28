package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    private UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        UserMeal meal = service.get(USER_MEAL_START_ID, USER_ID);
        MATCHER.assertEquals(meal, USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound(){
        service.get(ADMIN_MEAL_START_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound2(){
        service.get(ADMIN_MEAL_START_ID, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_MEAL_START_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(ADMIN_MEAL_START_ID, USER_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        Collection<UserMeal> meals = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 1), LocalDate.of(2015, Month.MAY, 30), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_1, USER_MEAL_2, USER_MEAL_3), meals);
    }

    @Test
    public void getBetweenDatesMinMax() throws Exception {
        Collection<UserMeal> meals = service.getBetweenDates(LocalDate.MIN, LocalDate.MAX, USER_ID);
        MATCHER.assertCollectionEquals(meals, service.getAll(USER_ID));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<UserMeal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 16, 0), LocalDateTime.of(2015, Month.MAY, 31, 0, 0), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_3), meals);
    }

    @Test
    public void getAll() throws Exception {
        Collection<UserMeal> meals = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_1, USER_MEAL_2, USER_MEAL_3, USER_MEAL_4, USER_MEAL_5,
                USER_MEAL_6), meals);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        UserMeal meal = new UserMeal(USER_MEAL_START_ID + 100, LocalDateTime.now(), "description", 200);
        service.update(meal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound2() throws Exception {
        UserMeal meal = ADMIN_MEAL_1;
        meal.setCalories(100);
        service.update(meal, USER_ID);
    }

    @Test
    public void save() throws Exception {
        UserMeal meal = new UserMeal(LocalDateTime.now(), "description", 200);
        service.save(meal, USER_ID);
    }

}