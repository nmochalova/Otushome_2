package com.otus.driver;

import com.otus.exeptions.DriverTypeNotSupported;
import org.openqa.selenium.WebDriver;

public interface IDriverFactory {
    public WebDriver getDriver() throws DriverTypeNotSupported;
}