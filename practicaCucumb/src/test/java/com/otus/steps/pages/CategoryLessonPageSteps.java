package com.otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.ru.Также;
import io.cucumber.java.ru.Тогда;
import pages.CategoryLessonsPage;

public class CategoryLessonPageSteps {
    @Inject
    private CategoryLessonsPage categoryLessonsPage;

    @Тогда("На странице отображается заголовок {string}")
    public void pageHeaderShouldBeSameAs(String expectedHeader) {
        categoryLessonsPage.pageHeaderSholdBeSameAs(expectedHeader);
    }

    @Также("Находим самый дорогой курс")
    public void findingMostExpensiveCourse() {
        categoryLessonsPage.findExpensiveCheapestCourse(true);
    }

    @Также("Находим самый дешевый курс")
    public void findingCheapestCourse() {
        categoryLessonsPage.findExpensiveCheapestCourse(false);
    }
}
