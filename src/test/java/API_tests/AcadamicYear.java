package API_tests;

import API_utils.ExcelUtil;
import API_utils.ExtentBase;
import API_utils.GlobalData;
import API_utils.PropertyManager;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class AcadamicYear extends ExtentBase {

    @Test
    public void testCreateAcademicYear() throws IOException {
        var test = ExtentManager.getTest();
        test.log(Status.INFO, "Starting Academic Year creation test");

        try {
            GlobalData.AccessToken();
            GlobalData.tokenValidate();
            test.log(Status.INFO, "Access token retrieved and validated successfully");

            String baseURI = "http://mpsapi1.microproindia.com:8080/CourseConfigService-1.0/course/ctrl/save_aca_year_mst";
            String filePath = "C:\\Users\\HP\\Downloads\\CourseConfigModule.xlsx";
            String sheetName = "Academic year";

            ExcelUtil excelUtils = new ExcelUtil(filePath);
            int rowCount = excelUtils.getRowCount(sheetName);

            for (int i = 1; i < 2; i++) {
                String startDate = excelUtils.getCellData(sheetName, i, 0);
                String endDate = excelUtils.getCellData(sheetName, i, 1);
                String startYr = excelUtils.getCellData(sheetName, i, 2);
                String endYr = excelUtils.getCellData(sheetName, i, 3);
                String description = excelUtils.getCellData(sheetName, i, 4);

                String payload = "{\r\n"
                        + "  \"yrms_closed\": \"NO\",\r\n"
                        + "  \"yrms_wf_tag1_code\": \"\",\r\n"
                        + "  \"yrms_wf_tag2_code\": \"\",\r\n"
                        + "  \"yrms_isactive\": \"YES\",\r\n"
                        + "  \"yrms_from_date\": \"" + startDate + "\",\r\n"
                        + "  \"yrms_to_date\": \"" + endDate + "\",\r\n"
                        + "  \"yrms_from_year\": \"" + startYr + "\",\r\n"
                        + "  \"yrms_to_year\": \"" + endYr + "\",\r\n"
                        + "  \"yrms_description\": \"" + description + "\",\r\n"
                        + "  \"yrms_entity_code_ent\": \"001001000000000000\",\r\n"
                        + "  \"yrms_isdraft\": \"NO\",\r\n"
                        + "  \"yrms_wf_doctype_dtyp\": \"CCLF01\",\r\n"
                        + "  \"yrms_isworkflowapproved\": \"YES\"\r\n"
                        + "}";

                RequestSpecification request = RestAssured.given();
                Response res = request
                        .header("Authorization", "Bearer " + GlobalData.tokenKey)
                        .header("Content-Type", "application/json")
                        .body(payload)
                        .post(baseURI);

                int statusCode = res.getStatusCode();
                String responseBody = res.getBody().asPrettyString();

                // ✅ Console logs
                System.out.println("Response Status Code: " + statusCode);
                System.out.println("Response Body:\n" + responseBody);

                // ✅ Extent logs
                test.log(Status.INFO, "Request Payload: <pre>" + payload + "</pre>");
                test.log(Status.INFO, "Response Status Code: " + statusCode);
                test.log(Status.INFO, "Response Body: <pre>" + responseBody + "</pre>");

                // ✅ Save response to file and link it
                try {
                    String folder = "test-output/api-responses";
                    Files.createDirectories(Paths.get(folder));
                    String filePathResponse = folder + "/response_" + System.currentTimeMillis() + ".json";
                    Files.write(Paths.get(filePathResponse), responseBody.getBytes());
                    test.log(Status.INFO, "📎 <a href='" + filePathResponse + "' target='_blank'>Download Response JSON</a>");
                } catch (Exception e) {
                    test.warning("Could not attach response file: " + e.getMessage());
                }

                if (statusCode != 200) {
                    test.fail("Unexpected status code: " + statusCode);
                    Assert.fail("API failed. Status code: " + statusCode + "\nResponse:\n" + responseBody);
                }

                test.pass("API returned expected status code 200");

                String l_id2 = res.jsonPath().getString("l_id");
                PropertyManager.setProperty("AcadamicyearId", l_id2);
                PropertyManager.setProperty("startDate", startDate);
                test.log(Status.INFO, "Saved Academic Year ID: " + l_id2);
            }

        } catch (Exception e) {
            test.fail("❌ Exception occurred: " + e.getMessage());
            test.fail("<pre>" + Arrays.toString(e.getStackTrace()) + "</pre>");
            throw e;
        } finally {
            test.log(Status.INFO, "Test execution completed.");
        }
    }
}