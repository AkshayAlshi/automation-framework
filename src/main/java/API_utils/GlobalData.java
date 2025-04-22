package API_utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GlobalData {

    public static String tokenKey;
    public static String refreshKey;
	public static String l_id;

    // Method to fetch access token (Login API)
    public static void AccessToken() {
        String endpoint = "all_login";
        String baseURI = "http://mpsapi1.microproindia.com:8080/AdminService-1.0/admin/ctrl/" + endpoint;

        // Create a RequestSpecification object
        RequestSpecification request = RestAssured.given();

        // Payload for login
        String payload = "{\r\n"
            + "    \"gmd_LoginId\":\"SYSTEM\",\r\n"
            + "    \"cclf_aca_year_mst\":\"YRMS000000000006\",\r\n"
            + "    \"cclf_aca_session_mst\":\"SSMS000000000018\",\r\n"
            + "    \"gmd_Password\":\"U1B5gu4eZp+pXP+DBSs/Fg==\"\r\n"
            + "}";

        // Set header
        request.header("Content-Type", "application/json");

        // Send the POST request to fetch the token
        Response res = request.body(payload).post(baseURI);

        // Get response body
        String body = res.getBody().asString();

        // Extract token and refresh key from the response
        tokenKey = res.jsonPath().getString("l_UserProfile.l_token");
        refreshKey = res.jsonPath().getString("l_UserProfile.l_refreshkey");

        System.out.println("Extracted token is: " + tokenKey);
        System.out.println("Extracted refreshkey is: " + refreshKey);
    }

    // Method to validate the token
    public static void tokenValidate() {
        String endpoint = "token_validate";
        String baseURI = "http://mpsapi1.microproindia.com:8080/AdminService-1.0/admin/ctrl/" + endpoint;

        // Create a RequestSpecification object
        RequestSpecification request = RestAssured.given();

        // Payload for token validation
        String payload = "{\r\n"
            + "  \"gmd_refreshkey\":\"" + refreshKey + "\",\r\n"
            + "  \"gmd_tokenkey\":\"" + tokenKey + "\"\r\n"
            + "}";

        // Set header
        request.header("Content-Type", "application/json");

        // Send the POST request for token validation
        Response res = request.body(payload).post(baseURI);

        // Get response body
        String body = res.getBody().asString();

        // Extract new refresh key from response (if provided)
        refreshKey = res.jsonPath().getString("gmd_refreshkey");

        System.out.println("Token validation response: " + body);
        System.out.println("Extracted Refreshkey from validation: " + refreshKey);
    }

    // Method to revalidate the token
    public static void revalidateToken() {
        String endpoint = "revalidate_token";
        String baseURI = "http://mpsapi1.microproindia.com:8080/AdminService-1.0/admin/ctrl/" + endpoint;

        // Create a RequestSpecification object
        RequestSpecification request = RestAssured.given();

        // Payload for token revalidation
        String payload = "{\r\n"
            + "  \"gmd_refreshkey\":\"" + refreshKey + "\",\r\n"
            + "  \"gmd_tokenkey\":\"" + tokenKey + "\"\r\n"
            + "}";

        // Set header
        request.header("Content-Type", "application/json");

        // Send the POST request for token revalidation
        Response res = request.body(payload).post(baseURI);

        // Get response body
        String body = res.getBody().asString();

        // Output the response for debugging
        System.out.println("Token revalidation response: " + body);
    }

    public static void main(String[] args) {
        // Test Access Token and Token Validation
        AccessToken();    
        tokenValidate(); 
        revalidateToken(); 
    }
}
