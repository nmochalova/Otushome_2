package pages;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import support.GuiceScoped;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoursePage extends BasePage<CoursePage> {

    @Inject
    public CoursePage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    public void checkTitleCourseByFilter(String title) {
      String expectedResult = guiceScoped.driver.findElement(By.tagName("title")).getAttribute("innerText");
      assertThat(expectedResult.toUpperCase()).contains(title.toUpperCase());
    }

    //Проверяем, что открылась страница в соответствии с выбранным курсом
    public void titlesShouldBeSomeMatch(String titleBeforeClick) {
        String titleAfterClick = getTitleByCourse(titleBeforeClick);

        assertThat(titleAfterClick.toUpperCase().contains(titleBeforeClick.toUpperCase()))
                .as("TEST_ERROR: The open page does not match the selected course.")
                .isTrue();
    }

    //Локаторы для разных курсов различаются
    public String getTitleByCourse(String titleBeforeClick) {
        By locator;

        if (!titleBeforeClick.toUpperCase().contains("СПЕЦИАЛИЗАЦИЯ"))
            locator = By.cssSelector(".course-header2__title");
        else
            locator = By.tagName("title");

        return guiceScoped.driver.findElement(locator).getAttribute("innerText");
    }
}
