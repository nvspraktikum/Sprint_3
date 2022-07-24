import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient(new RestAssuredClient());
    }

    @Test
    @DisplayName("Check that list of orders is returned")
    public void getOrderList() {
        ValidatableResponse orderList = orderClient.getListOrders().then().statusCode(200).and().body("orders.track", is(not(emptyArray())));
    }
}
