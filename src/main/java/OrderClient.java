import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderClient {
    int track;
    private final RestAssuredClient RestAssuredClient;

    public OrderClient(RestAssuredClient restAssuredClient) {
        RestAssuredClient = restAssuredClient;
    }

    private static final String ENDPOINT = "/api/v1/orders";

    @Step("Get list of orders")
    public Response getListOrders() {
        return RestAssuredClient.get(ENDPOINT);
    }

    @Step("Create order")
    public Response createOrder(Order order) {
        return RestAssuredClient.post(ENDPOINT, order);
    }

    @Step("Cancel created order")
    public Response cancelOrder(int track) {
        return RestAssuredClient.put(ENDPOINT + "/cancel?track=" + track);
    }

    public Response getOrder(int track){ return RestAssuredClient.get(ENDPOINT + "/track?t=" + track); }
}
