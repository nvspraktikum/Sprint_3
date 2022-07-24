import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        String postfix =  new Timestamp(System.currentTimeMillis()).toString();
        Courier courier = new Courier("login" + postfix, "password" + postfix, "name" + postfix);
        CourierClient.createCourier(courier);
        courierId = CourierClient.loginCourier(CourierCredentials.from(courier)).then().extract().body().path("id");
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Check login of courier with correct data")
    public void loginWithCorrectData() {
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setLogin("testCourierName1");
        courierCredentials.setPassword("testCourierPassword1");
        ValidatableResponse login = courierClient.loginCourier(courierCredentials)
                .then().statusCode(200).and().body("id", notNullValue());
    }

    @Test
    @DisplayName("Check login of courier with wrong login")
    public void loginWithIncorrectLogin() {
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setLogin("wrongLogin0");
        courierCredentials.setPassword("testCourierPassword1");
        ValidatableResponse login = courierClient.loginCourier(courierCredentials)
                .then().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check login of courier with empty password")
    public void loginEmptyPassword() {
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setLogin("testCourierName1");
        courierCredentials.setPassword("");
        ValidatableResponse login = courierClient.loginCourier(courierCredentials)
                .then().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }
}
