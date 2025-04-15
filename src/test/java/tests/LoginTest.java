package tests;

import base.BaseTest;
import config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(LoginTest.class);

    @Test
    public void validLogin() {
        log.info("Starting validLogin test...");

        String user = Config.get("username");
        String pass = Config.get("password");

        log.info("Navigating to URL: {}", Config.get("base.url"));
        driver.get(Config.get("base.url"));

        log.info("Attempting login with username: {}", user);
        new LoginPage(driver).login(user, pass);

        log.info("Verifying login success by checking URL contains 'UserInterfaces'");
        boolean isLoggedIn = driver.getCurrentUrl().contains("UserInterfaces");

        if (isLoggedIn) {
            log.info("✅ Login test passed.");
        } else {
            log.error("❌ Login test failed. URL after login: {}", driver.getCurrentUrl());
        }

        Assert.assertTrue(isLoggedIn, "User is not redirected to expected dashboard.");
    }
}
