package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/ExtentReport.html");
        reporter.config().setDocumentTitle("Automation Report");
        reporter.config().setReportName("UI/API Hybrid Framework Report");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void flushReport() {
        extent.flush();
    }
}
