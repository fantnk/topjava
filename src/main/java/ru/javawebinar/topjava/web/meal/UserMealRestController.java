package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    @Autowired
    private UserMealService service;

    public UserMeal save(UserMeal userMeal) {
        if (userMeal.getUserId() != LoggedUser.id())
            throw new NotFoundException("Не авторизован. ");

        return service.save(userMeal);
    }

    public UserMeal get(int id) {
        UserMeal userMeal = service.get(id);

        if (userMeal.getUserId() != LoggedUser.id())
            throw new NotFoundException("Не авторизован. ");

        return userMeal;
    }


    public void delete(int id) {
        UserMeal userMeal = service.get(id);

        if (userMeal.getUserId() != LoggedUser.id())
            throw new NotFoundException("Не авторизован. ");

        service.delete(id);
    }

    public Collection<UserMeal> getAll() {
        return service.getAll();
    }

}
