package API_tests;



import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import utils.ExtentManager;

import com.aventstack.extentreports.Status;

import API_utils.ExcelUtil;
import API_utils.GlobalData;
import API_utils.PropertyManager;

public class AcadamicYear {

    @Test
    public void testCreateAcademicYear() throws IOException {

        ExtentManager.createTest("testCreateAcademicYear");
        var test = ExtentManager.getTest();
        test.log(Status.INFO, "Starting Academic Year creation test");

        try {
            GlobalData.AccessToken();
            GlobalData.tokenValidate();
            test.log(Status.INFO, "Access token validated successfully");

            String baseURI = "http://mpsapi1.microproindia.com:8080/CourseConfigService-1.0/course/ctrl/save_aca_year_mst";
            String filePath = "C:\\Users\\HP\\Downloads\\CourseConfigModule.xlsx";
            ExcelUtil excelUtils = new ExcelUtil(filePath);
            String  sheetName = "Academic year";

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

                int actualStatusCode = res.getStatusCode();
                String responseBody = res.getBody().asPrettyString();

                test.log(Status.INFO, "API Response Code: " + actualStatusCode);
                test.log(Status.INFO, "API Response Body: <pre>" + responseBody + "</pre>");

                Assert.assertEquals(actualStatusCode, 200, "Expected status code 200");
                test.pass("Received expected status code: 200");

                String l_id2 = res.jsonPath().getString("l_id");
                PropertyManager.setProperty("AcadamicyearId", l_id2);
                PropertyManager.setProperty("startDate", startDate);
                test.log(Status.INFO, "Saved Academic Year ID: " + l_id2);
            }

        } catch (Exception e) {
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;

        } finally {
            test.log(Status.INFO, "Test execution completed.");
        }
    }
}