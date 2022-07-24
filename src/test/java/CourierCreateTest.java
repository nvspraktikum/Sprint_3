import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {
    private static CourierClient courierClient;
    private int courierId;

    @BeforeClass
    public static void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (courierId != 0)
            courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Check creation of courier with valid data")
    public void createNewCourier() {
        String postfix =  new Timestamp(System.currentTimeMillis()).toString();
        Courier courier = new Courier("login" + postfix, "password" + postfix, "name" + postfix);

        boolean isCourierCreated = courierClient.createCourier(courier)
                .then().statusCode(201)
                .extract()
                .path("ok");

        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).then().extract().body().path("id");
    }

    @Test
    @DisplayName("Check creation of courier without password should return error")
    public void createCourierWithoutPassword() {
        String postfix =  new Timestamp(System.currentTimeMillis()).toString();
        Courier courier = new Courier("login" + postfix, "", "name" + postfix);

        ValidatableResponse response = courierClient.createCourier(courier)
                .then().statusCode(400).and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check creation of courier without login should return error")
    public void createCourierWithoutUserName() {
        String postfix =  new Timestamp(System.currentTimeMillis()).toString();
        Courier courier = new Courier("", "password" + postfix, "name" + postfix);

        ValidatableResponse response = courierClient.createCourier(courier)
                .then().statusCode(400).and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check creation of duplicate courier should return error")
    public void createDuplicateCourier(){
        String postfix =  new Timestamp(System.currentTimeMillis()).toString();
        Courier courier = new Courier("login" + postfix, "password" + postfix, "name" + postfix);

        boolean isCourierCreated = courierClient.createCourier(courier)
                .then().statusCode(201)
                .extract()
                .path("ok");

        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).then().extract().body().path("id");

        ValidatableResponse createDuplicate = courierClient.createCourier(courier)
                .then().statusCode(409).and().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }
}