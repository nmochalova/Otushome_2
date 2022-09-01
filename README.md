# OtusHomeworkFirst

Команда запуска тестов:

`
clean test -DforkCount=0 -Dcucumber.filter.tags=@smoke
`

где 

**-Dcucumber.filter.tags** - параметр, в котором указывается тег тестов из файла .feature

## Проект содержит следующие тесты:

**courceCategory.feature** - тесты по второму домашнему заданию
- Реализовать выбор фабрики через фичу ("Я открываю браузер Chrome). Поддержаны браузеры: chrome, firefox, opera
- Поиск указанного курса (название курса задается в фиче) и его выбора (в случае если несколько, то выбирается случайный)
- Поиск курсов, стартующих в указанную дату или позже указанной даты и вывод информации о них в консоль (название, дата старта)
- Перейти в раздел Курсы > Подготовительные курсы, выбрать самый дорогой и самый дешевый курс при помощи filter и вывод информации о нем в консоль.

**courseMainPage.feature** - тесты из первого домашнего задания
- Метод выбора курса, стартующего раньше всех/позже всех (при совпадении дат - выбрать любой) при помощи reduce
