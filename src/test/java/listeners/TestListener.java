package listeners;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentManager;
import utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentManager.getTest().log(Status.INFO, "Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().log(Status.PASS, "Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
        ScreenshotUtil.capture(driver, result.getName());
        ExtentManager.getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.initReport();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flushReport();
    }
}
