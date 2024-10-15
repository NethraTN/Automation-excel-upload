package PageObjectModel;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage extends BasePage {
    
    public SignUpPage(WebDriver driver) {
        super(driver);
    }
    
    @FindBy(xpath="//input[@ng-reflect-name='username']")
    private WebElement UserName;
    
    @FindBy(xpath="//input[@id='mat-input-1']")
    private WebElement Password;
    
    @FindBy(xpath="//button[@type='submit']")
    private WebElement LoginButton;

    // Method to enter username with wait
    public void enterUserName(String userName) {
      //  explicitWait(UserName, Duration.ofSeconds(10));
       // UserName.sendKeys(userName);
    	
    	explicitWait(UserName, Duration.ofSeconds(10));
    	System.out.println("Attempting to enter username...");
    	UserName.sendKeys(userName);
    }

    // Method to enter password with wait
    public void enterPassword(String password) {
        explicitWait(Password, Duration.ofSeconds(10));
        Password.sendKeys(password);
    }

    // Method to click the login button with wait
    public void clickLoginButton() {
        explicitWait(LoginButton, Duration.ofSeconds(10));
        LoginButton.click();
    }

    // Explicit wait method
    private void explicitWait(WebElement element, Duration timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
