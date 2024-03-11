package RunTestCourier;

import Courier.Courier;
import api.CourierAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RunLoginCourierTest {
    CourierAPI courierAPI = new CourierAPI();
    Courier courier = new Courier("PNatalia1", "1234", "Natalia");
    private int courierId;

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courierAPI.createCourier(courier);
    }

    @After
    public void tearDown(){
        if (courierId != 0){
            courierAPI.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Successful login")
    @Description("Успешный логин, переданы все обязательные поля")
    public void successfulLoginCourierTest(){
        Response response = courierAPI.loginCourier(courier).then().statusCode(200).extract().response();
        assertThat(response.body().jsonPath().get("id"), notNullValue());
    }

    @Test
    @DisplayName("Unsuccessful login/incorrect login")
    @Description("Неуспешный логин, некорректный логин")
    public void incorrectLoginCourierTest(){
        Courier courier = new Courier("PNatali", "1234", "Natalia");
        Response response = courierAPI.loginCourier(courier).then().statusCode(404).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Unsuccessful login/incorrect password")
    @Description("Неуспешный логин, некорректный пароль")
    public void incorrectPasswordCourierTest(){
        Courier courier = new Courier("PNatalia", "1230", "Natalia");
        Response response = courierAPI.loginCourier(courier).then().statusCode(404).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Unsuccessful login/without login")
    @Description("Неуспешный логин, передано без поля логина")
    public void withoutLoginCourierTest(){
        Courier courier = new Courier("", "1234", "Natalia");
        Response response = courierAPI.loginCourier(courier).then().statusCode(400).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Unsuccessful login/without password")
    @Description("Неуспешный логин, передано без поля пароль")
    public void withoutPasswordCourierTest(){
        Courier courier = new Courier("PNatalia", "", "Natalia");
        Response response = courierAPI.loginCourier(courier).then().statusCode(400).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Unsuccessful login/without login and password")
    @Description("Неуспешный логин, передано без полей логин и  пароль")
    public void withoutLoghinCourierTest(){
        Courier courier = new Courier("", "", "Natalia");
        Response response = courierAPI.loginCourier(courier).then().statusCode(400).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Недостаточно данных для входа"));
    }

}
