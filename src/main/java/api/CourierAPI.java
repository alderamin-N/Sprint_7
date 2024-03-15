package api;
import Courier.Courier;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CourierAPI {
    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BaseURL.URL);
    }


    @Step("Создание нового курьера")
    public Response createCourier(Courier courier){
        return requestSpec()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Авторизация курьера в системе")
    public Response loginCourier(Courier courier){
        return requestSpec()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера")
    public Response deleteCourier(int courierId) {
        return requestSpec()
                .header("Content-type", "application/json")
                .delete("/api/v1/courier/{id}", courierId);
    }
}
