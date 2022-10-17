package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataGenerator {
    public DataGenerator() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    public static String setDate(int plusDays) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String setName() {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String setPhone() {
        return faker.numerify("+79#########");
    }

    public static String setCity() {
        return faker.address().cityName();
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
        String date;
    }
}
