package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    @FindBy(id = "loginfrm:j_u") private WebElement username;
    @FindBy(id = "loginfrm:j_p") private WebElement password;
    @FindBy(xpath = "//input[@type='submit']") private WebElement loginBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String user, String pass) {
        username.sendKeys(user);
        password.sendKeys(pass);
        loginBtn.click();
    }
}
