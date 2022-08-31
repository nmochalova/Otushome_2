package pages;

import com.otus.components.waiters.StandartWaiter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import support.GuiceScoped;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class BasePage<T> {
    protected GuiceScoped guiceScoped;
    protected StandartWaiter standartWaiter;        //явные ожидания

    public BasePage(GuiceScoped guiceScoped) {
        this.guiceScoped=guiceScoped;
        standartWaiter = new StandartWaiter(guiceScoped.driver);
        PageFactory.initElements(guiceScoped.driver, this);
    }

    @FindBy(css = "h1")
    private WebElement header;

    public T open(String path) {
        guiceScoped.driver.get(path);

        return (T)this;
    }

    public T pageHeaderSholdBeSameAs(String header) {
        String title = this.header.getText();
        assertThat(title.equals(header))
                .as("Titles should be equals")
                .isTrue();

        return (T)this;
    }

    public T clickToElement(WebElement element) {
        element.click();

        return (T)this;
    }

    public T moveToElement(WebElement element) {
        Actions actions = new Actions(guiceScoped.driver);
        actions.moveToElement(element).build().perform();

        return (T)this;
    }
}
