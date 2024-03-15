package RunTestCourier;
import Courier.Courier;
import api.CourierAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Courier.RandomCourier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RunCreateCourierTest {
    protected  final RandomCourier randomCourier = new RandomCourier();
    private CourierAPI courierAPI;
    private Courier courier;

    int courierId;

    @Before
    public void setUp(){
        courierAPI = new CourierAPI();
        courier = randomCourier.createRandomCourier();
    }

    @After
    public void tearDown(){
        if (courierId != 0){
            courierAPI.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Create new courier")
    @Description("Создание нового курьера")
    public void createNewCourierTest(){
        Response response = courierAPI.createCourier(courier).then().statusCode(201).extract().response();
        assertThat(response.body().jsonPath().get("ok"), equalTo(true));
    }

    @Test
    @DisplayName("Create again courier")
    @Description("Создание существующего курьера в системе")
    public void createAgainCourierTest(){
        courierAPI.createCourier(courier);
        Response response = courierAPI.createCourier(courier).then().statusCode(409).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Create courier without login")
    @Description("Создание курьера без логина")
    public void createCourierWithoutLoginTest(){
        courier.setLogin("");
        Response response = courierAPI.createCourier(courier).then().statusCode(400).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Create courier without password")
    @Description("Создание курьера без пароля")
    public void createCourierWithoutPasswordTest(){
        courier.setPassword("");
        Response response = courierAPI.createCourier(courier).then().statusCode(400).extract().response();
        assertThat(response.body().jsonPath().get("message"), equalTo("Недостаточно данных для создания учетной записи"));
    }
}
