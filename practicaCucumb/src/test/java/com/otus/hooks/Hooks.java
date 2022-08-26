package com.otus.hooks;

import com.google.inject.Inject;
import io.cucumber.java.After;
import support.GuiceScoped;

public class Hooks {
    @Inject
    private GuiceScoped guiceScoped;

    @After
    public void afterScenario() {
        if (guiceScoped.driver != null) {
            if (!guiceScoped.browserName.getName().equals("firefox")) guiceScoped.driver.close(); //для firefox close не делаем
            guiceScoped.driver.quit();
        }
    }

}
