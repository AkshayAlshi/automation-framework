package API_utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void initReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/reports/ExtentReport.html");
        reporter.config().setDocumentTitle("Automation Execution Report");
        reporter.config().setReportName("Hybrid UI/API Test Report");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    public static void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
