package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.UserMealsUtil.getWithExceeded;

public class UserMealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserMealRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        List<UserMealWithExceed> meals = getWithExceeded(USER_MEALS, AuthorizedUser.getCaloriesPerDay());

        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEED.contentListMatcher(meals)));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = new UserMeal(MEAL1);
        updated.setDescription("Обновленный завтрак");
        updated.setCalories(200);
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, AuthorizedUser.id()));
    }

    @Test
    public void testCreateWithLocation() throws Exception {
        UserMeal expected = new UserMeal(LocalDateTime.of(2016, 8, 8, 15, 0, 0), "Новая еда", 300);

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new UserMeal(expected)))).andExpect(status().isCreated());

        UserMealWithExceed returned = MATCHER_EXCEED.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned.getUserMeal());
        MATCHER.assertCollectionEquals(Arrays.asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1),
                mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testGetBetween() throws Exception {
        List<UserMealWithExceed> meals = getWithExceeded(USER_MEALS, AuthorizedUser.getCaloriesPerDay());
        meals.removeIf(meal -> meal.getUserMeal().equals(MEAL6));
        meals.removeIf(meal -> meal.getUserMeal().equals(MEAL3));

        TestUtil.print(mockMvc.perform(get(REST_URL + "filter").param("startDateTime", "2015-05-30T08:00:00")
                .param("endDateTime", "2015-05-31T13:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEED.contentListMatcher(meals)));
    }

}