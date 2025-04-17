package tests;

import base.BaseTest;
import config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentTest;

public class LoginTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(LoginTest.class);

    @Test
    public void validLogin() {
        ExtentTest test = ExtentManager.getTest();  // ✅ Get current ExtentTest instance
        log.info("Starting validLogin test...");
        test.info("Starting validLogin test...");

        String user = Config.get("username");
        String pass = Config.get("password");

        log.info("Navigating to URL: {}", Config.get("base.url"));
        test.info("Navigating to URL: " + Config.get("base.url"));
        driver.get(Config.get("base.url"));

        log.info("Attempting login with username: {}", user);
        test.info("Attempting login with username: " + user);
        new LoginPage(driver).login(user, pass);

        log.info("Verifying login success by checking URL contains 'UserInterfaces'");
        test.info("Verifying login success by checking URL contains 'UserInterfaces'");
        boolean isLoggedIn = driver.getCurrentUrl().contains("UserInterfaces");

        if (isLoggedIn) {
            log.info("✅ Login test passed.");
            test.pass("✅ Login test passed. Dashboard URL confirmed.");
        } else {
            log.error("❌ Login test failed. URL after login: {}", driver.getCurrentUrl());
            test.fail("❌ Login test failed. Actual URL: " + driver.getCurrentUrl());
        }

        Assert.assertTrue(isLoggedIn, "User is not redirected to expected dashboard.");
    }
}
