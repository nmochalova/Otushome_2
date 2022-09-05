package com.otus.steps.pages;

import com.google.inject.Inject;
import com.otus.datatable.DataTableCourse;
import io.cucumber.java.ru.*;
import org.openqa.selenium.WebElement;
import pages.CoursePage;
import pages.MainPage;
import java.util.List;

public class MainPageSteps {
    @Inject
    public MainPage mainPage;

    @Inject
    public CoursePage coursePage;

    @И("Открываю главную страницу сайта")
    public void openMainPage() {
        String hostname = System.getProperty("base.url");
        mainPage.open(hostname);
    }

    @Тогда("Главная страница открыта и заголовок {string}")
    public void pageShouldBeOpened(String expectedHeader) {
        mainPage.pageHeaderSholdBeSameAs(expectedHeader);
    }

    @И("Я выбираю курс в название которого содержится {string}")
    public void clickCourseByFilterName(String filterName) throws Exception {
        WebElement course = mainPage.getCourseByFilterName(filterName);

        mainPage.clickToElement(course);
    }

    @Также("Ищу курс стартующий на дату {string}")
    public void searchCourseByDate(String arg) throws Exception {
        Boolean isEq = true;
        List<DataTableCourse> dataTableCourse = mainPage.getCourseByDate(arg,isEq);
        printCourses(dataTableCourse, arg, isEq);
    }

    @Также("Ищу курсы стартующих после даты {string}")
    public void searchCoursesAfterDate(String arg) throws Exception {
        Boolean isEq = false;
        List<DataTableCourse> dataTableCourse = mainPage.getCourseByDate(arg,isEq);
        printCourses(dataTableCourse, arg, isEq);
    }

    private void printCourses(List<DataTableCourse> dataTableCourse, String arg, Boolean isEq) {
        String str = (isEq ? "На дату" : "Позже даты");
        System.out.println();
        System.out.println("***************************************");
        dataTableCourse.forEach(p ->
                System.out.println(String.format("%s %s найден курс: Описание = %s, Дата = %s",
                        str, arg, p.getName(), p.getDateString())));

        System.out.println("***************************************");
        System.out.println();
    }


    @И("Я выбираю самый ранний курс")
    public void chooseMostEarlyCourse() {
        System.out.println("*********** The most early course is ********************");
        String titleBeforeClick = mainPage.getLatestEarlyCourse(false);
        coursePage.titlesShouldBeSomeMatch(titleBeforeClick);
    }

    @И("Я выбираю самый поздний курс")
    public void chooseMostLatestCourse() {
        System.out.println("*********** The most latest course is ********************");
        String titleBeforeClick = mainPage.getLatestEarlyCourse(true);
        coursePage.titlesShouldBeSomeMatch(titleBeforeClick);
    }
}
