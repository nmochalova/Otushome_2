# language: ru
  @smoke
  Функционал: Страница категории курса

    #clean test -DforkCount=0 -Dcucumber.filter.tags=@test1
    @test1
    Структура сценария: Поиск курса по имени
      Пусть Я открываю браузер "chrome"
      И Открываю главную страницу сайта
      Тогда Главная страница открыта и заголовок "Авторские онлайн‑курсы для профессионалов"
      И Я выбираю курс в название которого содержится "<filter>"
      Тогда Страница выбранного курса открыта и заголовок содержит "<title>"
      Примеры:
        | filter                               | title                    |
        | Developer                            | Developer                |
        | QA                                   | QA                       |
        | Специализация сетевой инженер        | Network Engineer         |

      @test2
      Сценарий: Поиск курса на указанную дату
        Пусть Я открываю браузер "chrome"
        И Открываю главную страницу сайта
        Тогда Главная страница открыта и заголовок "Авторские онлайн‑курсы для профессионалов"
        Также Ищу курс стартующий на дату "29.09.2022"

       @test2
      Сценарий: Поиск курсов позже указанной даты
        Пусть Я открываю браузер "chrome"
        И Открываю главную страницу сайта
        Тогда Главная страница открыта и заголовок "Авторские онлайн‑курсы для профессионалов"
        Также Ищу курсы стартующих после даты "15.09.2022"
