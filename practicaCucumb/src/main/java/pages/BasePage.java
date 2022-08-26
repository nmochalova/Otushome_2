package pages;

import com.otus.components.waiters.StandartWaiter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import support.GuiceScoped;

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
        assert this.header.getText().equals(header): "Заголовки не равны";

        return (T)this;
    }
}
