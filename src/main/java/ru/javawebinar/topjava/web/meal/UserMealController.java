package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Controller
@RequestMapping(value = "/meals")
public class UserMealController extends AbstractUserMealController {

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(HttpServletRequest request) {
        request.setAttribute("meal", new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000));
        return "mealEdit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request) {
        request.setAttribute("meal", super.get(getId(request)));
        return "mealEdit";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        request.setAttribute("mealList", super.getBetween(startDate, startTime, endDate, endTime));

        return "mealList";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateOrCreate(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        final UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (userMeal.isNew()) {
            super.create(userMeal);
        } else {
            super.update(userMeal, userMeal.getId());
        }

        return "redirect:/meals";
    }

    /*@RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, String action, int id) {
        final UserMeal meal = action.equals("create") ?
                new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                super.get(id);             // update
        request.setAttribute("meal", meal);
        return "redirect:edit";
    }*/

    /*@RequestMapping(value = "/meals/{mealId}/delete", method = RequestMethod.GET)
    public String deleteMeal(@PathVariable("mealId") int mealId) {
        userMealRestController.delete(mealId);
        return "redirect:mealList";
    }*/

    /*@RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String setMeal(HttpServletRequest request) {
        int mealId = Integer.valueOf(request.getParameter("mealId"));
        return "redirect:meals";
    }*/

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
