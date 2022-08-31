package pages;

import com.google.inject.Inject;
import com.otus.data.Months;
import com.otus.datatable.DataTableCourse;
import com.otus.exeptions.NoDataFound;
import com.otus.exeptions.ValueIsEmpty;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainPage extends BasePage<MainPage> {
    @Inject
    public MainPage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    @FindBy(xpath = "//div[@class='lessons']//a[contains(@class,'lessons__new-item')]")
    private List<WebElement> allCourses;

    @FindBy(xpath = "//div[@class='container container-lessons']")
    private WebElement popularCourses;

    @FindBy(xpath = "//div[@class='container-padding-bottom']")
    private WebElement specializationsCourses;

    public String getNameOfCourse(WebElement course) {
        return course.findElement(By.className("lessons__new-item-title")).getText();
    }

    public HashMap<WebElement, DataTableCourse> getNamesAndDates() {
        HashMap<WebElement, DataTableCourse> nameAndDate = new HashMap<>();
        String nameCourse;
        String dateCourse;

        List<WebElement> blockPopular = popularCourses.findElements(By.xpath("./div[@class='lessons']/a"));
        for (WebElement element : blockPopular) {
            nameCourse = element
                    .findElement(By.xpath(".//div[contains(@class,'lessons__new-item-title')]"))
                    .getText();
            dateCourse = element
                    .findElement(By.xpath(".//div[@class='lessons__new-item-start']"))
                    .getText();

            nameAndDate.put(element, DataTableCourse.builder().name(nameCourse).dateString(dateCourse).build());
        }

        List<WebElement> blockSpecial = specializationsCourses.findElements(By.xpath("./div[@class='lessons']/a"));
        for (WebElement element : blockSpecial) {
            nameCourse = element
                    .findElement(By.xpath(".//div[contains(@class,'lessons__new-item-title')]"))
                    .getText();
            dateCourse = element
                    .findElement(By.xpath(".//div[@class='lessons__new-item-time']"))
                    .getText();

            nameAndDate.put(element,DataTableCourse.builder().name(nameCourse).dateString(dateCourse).build());
        }

        for(Map.Entry<WebElement, DataTableCourse> entry : nameAndDate.entrySet()) {
            Date dt = parserDateRegex(entry.getValue().getDateString());
            if (dt != null) {
                entry.getValue().setDate(dt);
            }
        }

        return nameAndDate;
    }


    //Метод выбора курса, стартующего раньше всех/позже всех (при совпадении дат - выбрать любой) при помощи reduce
    //isMax принимает значение "max" - для выбора курса, стартующего позже всех и "min" - раньше всех.
    public WebElement getMinMaxDateOfCourse(HashMap<WebElement, DataTableCourse> nameAndDate, Boolean isMax) {

        for(Map.Entry<WebElement, DataTableCourse> entry : nameAndDate.entrySet()) {
            Date dt = parserDateRegex(entry.getValue().getDateString());
            if (dt != null) {
                entry.getValue().setDate(dt);
            }
        }

        BinaryOperator<Map.Entry<WebElement, DataTableCourse>> binaryOperator = isMax ?
                (Map.Entry<WebElement, DataTableCourse> s1, Map.Entry<WebElement, DataTableCourse> s2)
                        -> (s1.getValue().getDate().after(s2.getValue().getDate()) ? s1 : s2):
                (Map.Entry<WebElement, DataTableCourse> s1, Map.Entry<WebElement, DataTableCourse> s2)
                        -> (s1.getValue().getDate().after(s2.getValue().getDate()) ? s2 : s1);

        WebElement result = nameAndDate.entrySet().stream()
                .filter(p -> p.getValue().getDate()!=null)
                .reduce(binaryOperator)
                .map(p -> p.getKey())
                .get();

        System.out.println("Выбран курс: " + result.getText());
        return result;
    }

    //Парсим строку в массив дат
    private Date parserDateRegex(String stringDateFromSite) {
        int day;
        String month;
        String year;
        Pattern p = Pattern.compile("(?<day>\\d{1,2})\\W{1,3}(?<month>янв|фев|мар|апр|май|июн|июл|авг|сен|окт|ноя|дек)\\W{1,2}(?<year>\\d{4})?",
                Pattern.CASE_INSENSITIVE+Pattern.UNICODE_CASE);
        Matcher m = p.matcher(stringDateFromSite);

        if(m.find()) {
            day = Integer.parseInt(m.group("day"));
            month = m.group("month");
            year = m.group("year");
            return stringToDate(day, month, year);
        } else
            return null;
    }

    //Преобразование строки в дату
    private Date stringToDate(int day, String month, String year) {
        LocalDate date = LocalDate.now();

        String monthNumber = getMonth(month);
        try {
            String str = String.format("%d/%s/%d", day, monthNumber, year==null ? date.getYear() : Integer.parseInt(year));
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            return formatter.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Date stringToDate(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMonth(String month) {
        String monthRUS = String.valueOf(month.toCharArray(), 0, 3);

        return Months.findMonth(monthRUS);
    }

    public List<String> getNamesAllCourse() {
        List<String> names = new ArrayList<>();
        for (WebElement element : allCourses) {
            names.add(element.findElement(By.xpath(".//div[contains(@class,'lessons__new-item-title')]")).getText());
        }
        return names;
    }

    //Метод фильтр по названию курса. Если filter=null, то возвращаем весь список
    public List<WebElement> filterCourseByName(HashMap<WebElement, DataTableCourse> names, String filterName) throws ValueIsEmpty {

        List<WebElement> result = names.entrySet().stream()
                .filter(p -> p.getValue().getName().toUpperCase().contains(filterName.toUpperCase()))
                .map(p -> p.getKey())
                .collect(Collectors.toList());

        return result;
    }

    public WebElement getCourseByFilterName(String filterName) throws Exception {
        //если фильтр не задан, то выкидываем эксепшен
        if(filterName == null || filterName.isEmpty())
            throw new ValueIsEmpty("filterName is empty");

        HashMap<WebElement, DataTableCourse> nameAndDate = getNamesAndDates();

        //фильтруем курсы по заданному фильтру
        List<WebElement> coursesAfterFilter = filterCourseByName(nameAndDate, filterName);

        //Если по заданному фильтру не найдено ни одного курса, то выкидываем эксепшен
        if (coursesAfterFilter.isEmpty())
            throw new NoDataFound("List of courses after filter is empty");

        //Иначе выбираем первый курс из отфильтрованного списка
        return coursesAfterFilter.get(0);
    }

    public List<DataTableCourse> getCourseByDate(String dateCourse, Boolean isEq) throws ValueIsEmpty, NoDataFound {
        //если дата не задана, то выкидываем эксепшен
        if(dateCourse == null)
            throw new ValueIsEmpty("Date of course is empty");

        HashMap<WebElement, DataTableCourse> nameAndDate = getNamesAndDates();
        List<DataTableCourse> courseAfterFilterByDate;

        if (isEq) {  //ищем курсы на указанную дату
              courseAfterFilterByDate = filterCourseByDate(nameAndDate,
                (Date d)->d.equals(stringToDate(dateCourse)));}
        else {      //ищем курсы после указанной даты
              courseAfterFilterByDate = filterCourseByDate(nameAndDate,
                (Date d)->d.after(stringToDate(dateCourse)));}

        //Если по заданному фильтру не найдено ни одного курса, то выкидываем эксепшен
        if (courseAfterFilterByDate.isEmpty())
            throw new NoDataFound("Course is not found by date filter");

        return courseAfterFilterByDate;
    }

    private List<DataTableCourse> filterCourseByDate(HashMap<WebElement, DataTableCourse> nameAndDate,
                                                     Predicate<Date> filterPredicate) {

        List<DataTableCourse> result = nameAndDate.entrySet().stream()
                .filter(p -> p.getValue().getDate()!=null)
                .filter(p -> filterPredicate.test(p.getValue().getDate()))
                .map(p -> p.getValue())
                .collect(Collectors.toList());

        return result;
    }
}
