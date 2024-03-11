package RunTestOrder;


import api.OrderAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import Order.Order;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)
public class RunCreateOrderTest {

    OrderAPI orderAPI = new OrderAPI();
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Number rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public RunCreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, Number rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getTestDate() {
        String[] colorGrey = {"GREY"};
        String[] colorBlaCk = {"BLACK"};
        String[] colorAll = {"GREY", "BLACK"};
        String[] withoutColor = {};
        return new Object[][]{
                {"Petr", "Petrov", "Nevskiy,123", "Vostania", "+7 963 963 96 63", 1, "2024-03-10", "my comment",colorGrey },
                {"Petr", "Petrov", "Nevskiy,123", "Vostania", "+7 963 963 96 63", 2, "2024-03-11", "my comment",colorBlaCk },
                {"Petr", "Petrov", "Nevskiy,123", "Vostania", "+7 963 963 96 63", 3, "2024-03-12", "my comment",colorAll},
                {"Petr", "Petrov", "Nevskiy,123", "Vostania", "+7 963 963 96 63", 4, "2024-03-13", "my comment",withoutColor},
        };
    }
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Create order")
    @Description("Создание заказа")
    public void createOrder(){
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = orderAPI.createOrder(order).then().statusCode(201).extract().response();
        assertThat(response.body().jsonPath().get("track"), notNullValue());
    }
}
