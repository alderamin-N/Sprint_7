package RunTestOrder;

import api.OrderAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class RunGetOrdersTest {
    OrderAPI orderAPI = new OrderAPI();


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Get orders")
    @Description("Получение списка заказов")
    public void getOrders(){
    Response response = orderAPI.getOrders().then().statusCode(200).extract().response();
    MatcherAssert.assertThat(response, notNullValue());
    }

}
