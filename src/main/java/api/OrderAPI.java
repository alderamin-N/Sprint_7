package api;


import Order.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderAPI {
    @Step("Создание заказа")
    public Response createOrder(Order order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получение списка заказа")
    public Response getOrders(){
        return given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders");
    }
}
