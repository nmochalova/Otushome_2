package pages;

import com.google.inject.Inject;
import com.otus.datatable.DataTableCourse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CategoryLessonsPage extends BasePage<CategoryLessonsPage>{
    @Inject
    public CategoryLessonsPage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    @FindBy(css = ".lessons__new-item")
    private List<WebElement> courseElements;

    public void findExpensiveCheapestCourse(Boolean isExpensive) {
        HashMap<WebElement, DataTableCourse> nameAndPrices = getNamesAndPrices();
        System.out.println("*********** The most "
                + (isExpensive ? "expensive" : "cheapest")
                + " courses ***********************");


        IntSummaryStatistics minMax = nameAndPrices.entrySet()
                .stream()
                .mapToInt(p -> p.getValue().getPrice())
                .summaryStatistics();

        int price = isExpensive? minMax.getMax() : minMax.getMin();


        List<DataTableCourse> coursesToPrice = getCoursesToPrice(nameAndPrices,price);
        printResult(coursesToPrice);
    }

    private void printResult(List<DataTableCourse> coursesToPrice) {
        coursesToPrice
                .stream()
                .map(p -> p.getName() + " " + p.getPriceString())
                .forEach(System.out::println);
    }

    //Получаем массив с названием курса и его цену
    public HashMap<WebElement, DataTableCourse> getNamesAndPrices() {
        HashMap<WebElement, DataTableCourse> nameAndPrice = new HashMap<>();

        for (WebElement element : courseElements) {
            String nameCourse = element
                    .findElement(By.cssSelector(".lessons__new-item-title"))
                    .getText();
            String priceCourse = element
                    .findElement(By.cssSelector(".lessons__new-item-price"))
                    .getText();

            int priceInt = getPriceInt(priceCourse);

            nameAndPrice.put(element,  DataTableCourse.builder().name(nameCourse).priceString(priceCourse).price(priceInt).build());
        }

        return nameAndPrice;
    }

    private int getPriceInt(String priceCourse) {
        Matcher m = Pattern.compile("(\\d+)").matcher(priceCourse);

        int priceInt;
        if(m.find())
            priceInt =Integer.parseInt(m.group(0));
        else
            throw new RuntimeException("Не найдена цена");
        return priceInt;
    }


    //Находим все курсы с указанной ценой
    public  List<DataTableCourse> getCoursesToPrice(HashMap<WebElement, DataTableCourse> nameAndPrice, int price) {
       return nameAndPrice.entrySet().stream()
            .filter(p -> p.getValue().getPrice() == price)
            .map(p -> p.getValue())
            .collect(Collectors.toList());
    }
}
