package api;

import Order.Order;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class OrderAPI {
    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BaseURL.URL);
    }

    @Step("Создание заказа")
    public Response createOrder(Order order){
        return requestSpec()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получение списка заказа")
    public Response getOrders(){
        return requestSpec()
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .get("/api/v1/orders");

    }
}
