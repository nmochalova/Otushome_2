package pages;

import com.google.inject.Inject;
import com.otus.datatable.DataTableCourse;
import com.otus.datatable.DataTableCoursePrice;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class CategoryLessonsPage extends BasePage<CategoryLessonsPage>{
    @Inject
    public CategoryLessonsPage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    @FindBy(css = ".lessons__new-item")
    private List<WebElement> courseElements;

    public void findExpensiveCheapestCourse(Boolean isExpensive) {
        HashMap<WebElement, DataTableCoursePrice> nameAndPrices = getNamesAndPrices();
        System.out.println("*********** The most "
                + (isExpensive ? "expensive" : "cheapest")
                + " courses ***********************");

        int price = 0;
        if (isExpensive)
            price = getMaxPrice(nameAndPrices);
        else
            price = getMinPrice(nameAndPrices);

        List<DataTableCoursePrice> coursesToPrice = getCoursesToPrice(nameAndPrices,price);
        printResult(coursesToPrice);
    }

    private void printResult(List<DataTableCoursePrice> coursesToPrice) {
        coursesToPrice
                .stream()
                .map(p -> p.getName() + " " + p.getPriceString())
                .forEach(System.out::println);
    }

    //Получаем массив с названием курса и его цену
    public HashMap<WebElement, DataTableCoursePrice> getNamesAndPrices() {
        HashMap<WebElement, DataTableCoursePrice> nameAndPrice = new HashMap<>();

        for (WebElement element : courseElements) {
            String nameCourse = element
                    .findElement(By.cssSelector(".lessons__new-item-title"))
                    .getText();
            String priceCourse = element
                    .findElement(By.cssSelector(".lessons__new-item-price"))
                    .getText();

            int priceInt = Integer.parseInt(priceCourse.replace(" ₽",""));

            nameAndPrice.put(element, new DataTableCoursePrice(nameCourse, priceCourse, priceInt));
        }

        return nameAndPrice;
    }

    //Находим максимальную цену курсов
    public int getMaxPrice(HashMap<WebElement, DataTableCoursePrice> nameAndPrice) {
        return nameAndPrice.entrySet()
                .stream()
                .mapToInt(p -> p.getValue().getPrice())
                .max()
                .getAsInt();
    }

    //Находим минимальную цену курсов
    public int getMinPrice(HashMap<WebElement, DataTableCoursePrice> nameAndPrice) {
        return nameAndPrice.entrySet()
                .stream()
                .mapToInt(p -> p.getValue().getPrice())
                .min()
                .getAsInt();
    }

    //Находим все курсы с указанной ценой
    public  List<DataTableCoursePrice> getCoursesToPrice(HashMap<WebElement, DataTableCoursePrice> nameAndPrice, int price) {
       return nameAndPrice.entrySet().stream()
            .filter(p -> p.getValue().getPrice() == price)
            .map(p -> p.getValue())
            .collect(Collectors.toList());
    }
}
