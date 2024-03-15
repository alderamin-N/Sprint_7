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
import Courier.RandomCourier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RunLoginCourierTest {
    protected  final RandomCourier randomCourier = new RandomCourier();
    private CourierAPI courierAPI;
    private Courier courier;

    int courierId;


    @Before
    public void setUp(){
        courierAPI = new CourierAPI();
        courier = randomCourier.createRandomCourier();
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
        Courier incorrectLoginCourier = new Courier(RandomCourier.NEW_INVALID_LOGIN, courier.getPassword());
        Response response = courierAPI.loginCourier(incorrectLoginCourier).then().statusCode(404).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Unsuccessful login/incorrect password")
    @Description("Неуспешный логин, некорректный пароль")
    public void incorrectPasswordCourierTest(){
        Courier incorrectPasswordCourier = new Courier(courier.getLogin(), RandomCourier.NEW_INVALID_PASSWORD);
        Response response = courierAPI.loginCourier(incorrectPasswordCourier).then().statusCode(404).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Unsuccessful login/without login")
    @Description("Неуспешный логин, передано без поля логина")
    public void withoutLoginCourierTest(){
        Courier CourierWithoutLogin = new Courier("", courier.getPassword());
        Response response = courierAPI.loginCourier(CourierWithoutLogin).then().statusCode(400).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Unsuccessful login/without password")
    @Description("Неуспешный логин, передано без поля пароль")
    public void withoutPasswordCourierTest(){
        Courier CourierWithoutPassword = new Courier(courier.getLogin(), "");
        Response response = courierAPI.loginCourier(CourierWithoutPassword).then().statusCode(400).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Unsuccessful login/without login and password")
    @Description("Неуспешный логин, передано без полей логин и  пароль")
    public void withoutLoghinCourierTest(){
        Courier courierWithoutAll = new Courier("", "", courier.getFirstName());
        Response response = courierAPI.loginCourier(courierWithoutAll).then().statusCode(400).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Недостаточно данных для входа"));
    }

}
