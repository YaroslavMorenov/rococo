package org.rococo.util;

import com.github.javafaker.Faker;

public class FakerUtils {

    private static final Faker faker = new Faker();

    public static String generateRandomUsername() {
        return faker.name().username();
    }

    public static String generateRandomPassword() {
        return faker.bothify("????####");
    }

    public static String generateRandomName() {
        return faker.name().firstName();
    }

    public static String generateRandomSurname() {
        return faker.name().lastName();
    }

    public static String generateRandomSentence(int wordsCount) {
        return faker.lorem().sentence(wordsCount);
    }

    public static String generateRandomString(int numberOfLetters) {
        faker.book().title();
        return faker.lorem().fixedString(numberOfLetters);
    }

    public static String generateRandomTitle() {
        return faker.book().title();
    }

    public static String generateRandomCity() {
        return faker.address().city();
    }
}
