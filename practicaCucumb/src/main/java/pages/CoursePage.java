package pages;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import support.GuiceScoped;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoursePage extends BasePage<CoursePage> {

    @Inject
    public CoursePage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    public void checkTitleCourseByFilter(String title) {
      assertThat(guiceScoped.driver.findElement(By.tagName("title")).getAttribute("innerText")).contains(title);
    }

}
