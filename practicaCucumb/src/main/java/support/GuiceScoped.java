package support;

import com.otus.data.BrowserData;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;

@ScenarioScoped
public class GuiceScoped {
    public BrowserData browserName;
    public WebDriver driver;
}
