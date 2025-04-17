package base;

import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExtentManager;
import utils.ScreenshotUtil;

import com.aventstack.extentreports.ExtentTest;

public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite
    public void initReport() {
        ExtentManager.initReport();
    }

    @BeforeMethod
    public void setup(Method method) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Create Extent test instance
        ExtentManager.createTest(method.getName());
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        ExtentTest test = ExtentManager.getTest();

        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtil.capture(driver, result.getName());
            test.fail("❌ Test failed: " + result.getThrowable());
            test.addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("✅ Test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("⚠️ Test skipped: " + result.getThrowable());
        }

        driver.quit();
    }

    @AfterSuite
    public void flushReport() {
        ExtentManager.flushReport();
    }
}
