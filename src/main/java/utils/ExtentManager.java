package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    
    // Thread-safe: handles parallel test execution
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void initReport() {
        // Output report in target directory (Jenkins can archive this easily)
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/ExtentReport.html");
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
