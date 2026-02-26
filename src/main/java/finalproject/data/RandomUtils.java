package finalproject.data;

import com.github.javafaker.Faker;

// Сгененрированные данные для тестирования
public class RandomUtils {

    private static final Faker faker = new Faker();
    public static String randomEmail() {
        return faker.internet().emailAddress();
    }
    public static String randomPassword() {
        return faker.internet().password(10, 20, true, true, true);
    }
    public static String getAction() {
        String[] actions = {"куплю", "продам", "обменяю", "сдам", "сниму"};
        return actions[faker.number().numberBetween(0, actions.length - 1)];
    }

    public static String randomName() {
        return getAction() + " " + faker.commerce().productName();
    }
    public static String randomDescription() {
        return "Описание товара " + faker.lorem().sentence(30);
    }
    public static Integer randomPrice() {
        return faker.number().numberBetween(100, 100000);
    }
}