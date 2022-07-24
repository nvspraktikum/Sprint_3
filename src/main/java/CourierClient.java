import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CourierClient extends RestAssuredClient {
    public CourierClient() {
    }

    private static final String ENDPOINT = "api/v1/courier/";

    @Step("Create courier")
    public static Response createCourier(Courier courier) {
        return RestAssuredClient.post(ENDPOINT, courier);
    }

    @Step("Login courier")
    public static Response loginCourier(CourierCredentials credentials) {
        return RestAssuredClient.post(ENDPOINT + "login/", credentials);
    }

    @Step("Delete courier")
    public Response deleteCourier(int courierId) {
        return RestAssuredClient.delete(ENDPOINT + courierId);
    }
}
