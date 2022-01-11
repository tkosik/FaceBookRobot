import org.checkerframework.checker.units.qual.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebFormLoginPage extends PageObject{
    WebDriverWait wait = new WebDriverWait(driver, 25);
    // Pliki cookies
    @FindBy(xpath = "//*[contains(text(),'Zezwolić')]")
    WebElement cookiesData;

    //Zezwól na wszystkie pliki cookie
    @FindBy(xpath = "//button[@title='Zezwól na wszystkie pliki cookie']")
    WebElement acceptCookies;

    //Adres mailowy
    @FindBy(xpath = "//input[@placeholder='Adres e-mail lub numer telefonu']")
    WebElement inputLog;

    //Hasło
    @FindBy(xpath="//input[@name='pass']")
    WebElement inputPass;

    //Zaloguj się
    @FindBy(xpath = "//button[text()='Zaloguj się']")
    WebElement buttonLogIn;

    //MarkaPlac
    @FindBy(xpath = "//a[contains(@aria-label,'Marketplace')] | //*[contains(text(),'Marketplace')]//parent::span//parent::div")
    WebElement buttonMarkaPlac;

    public WebFormLoginPage(WebDriver driver){
        super(driver);
    }

    // WebMethods
    public boolean isCookiesMessageVisible(){
        return this.cookiesData.isDisplayed();
    }

    public void clickAcceptCookiesButton(){
        this.acceptCookies.click();
    }

    public void inputPassword(String password){
        this.inputPass.sendKeys(password);
    }

    public void inputLogin(String login){
        this.inputLog.sendKeys(login);
    }

    public void clickLogInButton(){
        this.buttonLogIn.click();
        wait.until(ExpectedConditions.visibilityOf(buttonMarkaPlac));
        System.out.println("Poprawnie zalogowano!");
    }
    public void clickMarkPlacButton() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(buttonMarkaPlac));
        this.buttonMarkaPlac.click();
    }
}
