package com.otus.driver;

import com.google.inject.Inject;
import com.otus.driver.impl.ChromeWebDriver;
import com.otus.driver.impl.FireFoxWebDriver;
import com.otus.driver.impl.OperaWebDriver;
import com.otus.exeptions.DriverTypeNotSupported;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import support.GuiceScoped;

public class DriverFactory implements IDriverFactory {
    public GuiceScoped guiceScoped;

    @Inject
    public DriverFactory(GuiceScoped guiceScoped) {
        this.guiceScoped = guiceScoped;
    }

    @Override
    public EventFiringWebDriver getDriver() {
        switch (guiceScoped.browserName) {
            case CHROME: {
                return new EventFiringWebDriver(new ChromeWebDriver().newDriver());
            }
            case FIREFOX: {
                return new EventFiringWebDriver(new FireFoxWebDriver().newDriver());
            }
            case OPERA: {
                return new EventFiringWebDriver(new OperaWebDriver().newDriver());
            }
            default:
                try {
                    throw new DriverTypeNotSupported(guiceScoped.browserName.getName());
                } catch (DriverTypeNotSupported ex) {
                    ex.printStackTrace();
                    return null;
                }
        }
    }
}
