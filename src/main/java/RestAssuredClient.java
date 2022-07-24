import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestAssuredClient {
    public static RequestSpecification getRequestSpecification() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                .build();
    }

    public static <T> Response post(String path, T body, Object... pathParams) {
        return given()
                .spec(getRequestSpecification())
                .body(body)
                .when()
                .post(path, pathParams);
    }

    public static Response delete(String path, Object... pathParams) {
        return given()
                .spec(getRequestSpecification())
                .when()
                .delete(path, pathParams);
    }

    public <T> Response get(String path) {
        return given()
                .spec(getRequestSpecification())
                .get(path);
    }

    public <T> Response put(String path, Object... pathParams) {
        return given()
                .spec(getRequestSpecification())
                .when()
                .put(path, pathParams);
    }
}
