package tests;

import base.BaseTest;
import config.Config;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExcelUtils;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = ExcelUtils.class)
    public void validLogin(String user, String pass) {
        driver.get(Config.get("base.url"));
        new LoginPage(driver).login(user, pass);
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }
}