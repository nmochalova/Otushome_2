package com.otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.ru.А;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Также;
import pages.CoursePage;

import java.util.List;

public class CoursePageSteps {
    @Inject
    public CoursePage coursePage;

    @А("Я выбираю курс в название которого содержится {string}")
    public void clickCourseByFilterName(String filterName) {
        List<String> names = coursePage.getNamesAllCourse();

        if (filterName == null)
            names.forEach(System.out::println);
        else{
            List<String> namesAfterFilter = coursePage.filterCourseByName(names, filterName);
            namesAfterFilter.forEach(System.out::println);
        }
    }
}
