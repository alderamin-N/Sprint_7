package RunTestOrder;

import Order.Order;
import api.OrderAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class RunGetOrdersTest {

    OrderAPI orderAPI;

    @Before
    public void setUp() {
        orderAPI = new OrderAPI();
    }

    @Test
    @DisplayName("Get orders")
    @Description("Получение списка заказов")
    public void getOrders(){
        Response response = orderAPI.getOrders().then().statusCode(200).extract().response();
        MatcherAssert.assertThat(response.body(), notNullValue());
    }

}
