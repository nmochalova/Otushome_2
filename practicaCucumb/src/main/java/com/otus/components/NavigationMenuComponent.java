package com.otus.components;

import com.google.inject.Inject;
import com.otus.data.CategoryCourse;
import pages.CategoryLessonsPage;
import support.GuiceScoped;

public class NavigationMenuComponent extends BaseComponent<NavigationMenuComponent>{
    @Inject
    public NavigationMenuComponent(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    public CategoryLessonsPage clickNavItem(String itemName) {
        String path = getPathOfCategoryCourse(itemName);
        String url = System.getProperty("base.url") + path;
        guiceScoped.driver.get(url);

        return new CategoryLessonsPage(guiceScoped);
    }

    public String getPathOfCategoryCourse(String itemName) {
        return CategoryCourse.searchPathOfCourseByName(itemName);
    }
}
