package Courier;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class RandomCourier {
    static Faker faker = new Faker();

    public static final String NEW_INVALID_LOGIN = faker.name().username();
    public static final String NEW_INVALID_PASSWORD = faker.internet().password();
    @Step("Создание нового курьера рандом")
    public Courier createRandomCourier(){
        return new Courier(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName()
        );
    }
}
