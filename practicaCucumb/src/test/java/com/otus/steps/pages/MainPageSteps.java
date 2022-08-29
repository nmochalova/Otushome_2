package com.otus.steps.pages;

import com.google.inject.Inject;
import com.otus.exeptions.ValueIsEmpty;
import io.cucumber.java.ru.А;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.WebElement;
import pages.MainPage;

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

//        mainPage.moveToElement(course);
        mainPage.clickToElement(course);
    }
}
