package com.otus.steps.common;

import com.google.inject.Inject;
import com.otus.data.BrowserData;
import com.otus.driver.DriverFactory;
import io.cucumber.java.ru.Пусть;
import support.GuiceScoped;

import java.util.Locale;

public class CommonPageSteps {
    @Inject
    private DriverFactory driverFactory;
    @Inject
    private GuiceScoped guiceScoped;

    @Пусть("Я открываю браузер {string}")
    public void initBrowser(String browserName) {
        BrowserData browserData = BrowserData.valueOf(browserName.toUpperCase(Locale.ROOT));
        guiceScoped.browserName = browserData;
        guiceScoped.driver = driverFactory.getDriver();
    }
}
