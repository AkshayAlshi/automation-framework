package API_utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIUtils {

    public static Response get(String url) {
        return RestAssured.given()
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public static Response post(String url, String jsonPayload) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public static void printResponse(Response response) {
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.getBody().prettyPrint());
    }
}