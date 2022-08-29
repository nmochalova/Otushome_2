package com.otus.steps.pages;

import com.google.inject.Inject;
import com.otus.datatable.DataTableCourse;
import com.otus.exeptions.NoDataFound;
import com.otus.exeptions.ValueIsEmpty;
import com.sun.javafx.binding.StringFormatter;
import io.cucumber.java.ru.*;


import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Format;
import org.openqa.selenium.WebElement;
import pages.MainPage;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainPageSteps {
    @Inject
    public MainPage mainPage;

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
        List<DataTableCourse> dataTableCourse = mainPage.getCourseByDate(arg);

        System.out.println();
        System.out.println("***************************************");
        dataTableCourse.forEach(p ->
                System.out.println(String.format("На дату %s найден курс: Описание = %s, Дата = %s",
                        arg, p.getName(), p.getDateString())));

        System.out.println("***************************************");
        System.out.println();

    }

    @Также("^Ищу курсы стартующих после даты (.+)$")
    public void searchCoursesAfterDate( Date arg) {
        System.out.println(arg);
    }

}
