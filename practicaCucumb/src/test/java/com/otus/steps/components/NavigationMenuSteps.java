package com.otus.steps.components;

import com.google.inject.Inject;
import com.otus.components.NavigationMenuComponent;
import io.cucumber.java.ru.Если;

public class NavigationMenuSteps {
    @Inject
    private NavigationMenuComponent navigationMenuComponent;
    @Если("Кликнуть на категорию курса {string}")
    public void clickNavMenuItem(String itemName) {
        navigationMenuComponent.clickNavItem(itemName);
    }
}
