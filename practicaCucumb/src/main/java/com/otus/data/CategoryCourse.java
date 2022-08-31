package com.otus.data;

public enum CategoryCourse {
    PROGRAMMING("Программирование","/categories/programming"),
    INFRASTRUCTURE("Инфраструктура","/categories/operations"),
    DATA_SCIENCE("Data Science","/categories/data-science"),
    GAME_DEV("GameDev","/categories/gamedev"),
    MANAGEMENT("Управление","/categories/marketing-business"),
    ANALYTICS("Аналитика","/categories/analytics"),
    TESTING("Тестирование","/categories/testing"),
    CORPORATE_COURSE("Корпоративные курсы","/categories/corporate"),
    SPECIALIZATION("Специализации","/categories/spec"),
    DEVELOPMENT_COURSE("Курсы в разработке","/greenlight"),
    PREPARATORY_COURSE("Подготовительные курсы","/online"),
    SUBSCRIPTION("Подписка","/subscription");

    private String nameCategoryCourse;
    private String pathCategoryCourse;

    CategoryCourse(String nameCategoryCourse, String pathCategoryCourse) {
        this.nameCategoryCourse = nameCategoryCourse;
        this.pathCategoryCourse = pathCategoryCourse;
    }

    public String getNameCategoryCourse() {
        return nameCategoryCourse;
    }

    public String getPathCategoryCourse() {
        return pathCategoryCourse;
    }

    public static String searchPathOfCourseByName(String nameCourse) {
        for(CategoryCourse categoryCourse : CategoryCourse.values()) {
            if (categoryCourse.nameCategoryCourse.equals(nameCourse))
                return categoryCourse.pathCategoryCourse;
        }
        return null;
    }
}
