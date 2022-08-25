package com.otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
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
}
