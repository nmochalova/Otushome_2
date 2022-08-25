package pages;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoursePage extends BasePage<CoursePage> {

    @Inject
    public CoursePage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    @FindBy(xpath = "//div[@class='lessons']//a[contains(@class,'lessons__new-item')]")
    private List<WebElement> allCourses;

    public List<String> getNamesAllCourse() {
        List<String> names = new ArrayList<>();
        for (WebElement element : allCourses) {
            names.add(element.findElement(By.xpath(".//div[contains(@class,'lessons__new-item-title')]")).getText());
        }
        return names;
    }

    //Метод фильтр по названию курса
    public List<String> filterCourseByName(List<String> names, String name) {
        return names.stream().filter(p -> p.contains(name)).collect(Collectors.toList());
    }

    public String getTitleByCourse(String titleBeforeClick) {
        By locator;

        //Локаторы для разных курсов различаются
        if (!titleBeforeClick.toUpperCase().contains("СПЕЦИАЛИЗАЦИЯ"))
            locator = By.cssSelector(".course-header2__title");
         else
             locator = By.tagName("title");

        return guiceScoped.driver.findElement(locator).getAttribute("innerText"); //Если эл-т не видим на странице, то getText() возвращает пустую строку, поэтому берем текст напрямую из свойств эл-та.
    }

}
