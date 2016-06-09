package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.UserMealsUtil.DEFAULT_CALORIES;
import static ru.javawebinar.topjava.util.UserMealsUtil.MEAL_LIST;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");
        request.setAttribute("mealList", UserMealsUtil.getWithExceeded(MEAL_LIST(), DEFAULT_CALORIES));
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }
}
