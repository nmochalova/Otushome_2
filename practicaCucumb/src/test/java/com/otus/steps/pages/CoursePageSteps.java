package com.otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.ru.Тогда;
import pages.CoursePage;

public class CoursePageSteps {
    @Inject
    public CoursePage coursePage;

    @Тогда("Страница выбранного курса открыта и заголовок содержит {string}")
    public void openPageShouldBeSomeAsClickCourse(String title){
        coursePage.checkTitleCourseByFilter(title);
    }
}
