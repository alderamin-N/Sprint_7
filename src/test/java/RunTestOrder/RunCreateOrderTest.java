package RunTestOrder;

import api.OrderAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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
   private OrderAPI orderAPI;

    private String[] color;

    public RunCreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getTestDate() {
        String[] colorGrey = {"GREY"};
        String[] colorBlaCk = {"BLACK"};
        String[] colorAll = {"GREY", "BLACK"};
        String[] withoutColor = {};
        return new Object[][]{
                {colorGrey},
                {colorBlaCk},
                {colorAll},
                {withoutColor},
        };
    }
    @Before
    public void setUp() {
        orderAPI = new OrderAPI();
    }

    @Test
    @DisplayName("Create order")
    @Description("Создание заказа")
    public void createOrder(){
        Order order = new Order(color);
        Response response = orderAPI.createOrder(order).then().statusCode(201).extract().response();
        assertThat(response.body().jsonPath().get("track"), notNullValue());
    }
}
