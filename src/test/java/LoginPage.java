import jdk.jshell.execution.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginPage {
    static WebDriver driver;
    FilePage filePage = new FilePage();

    @BeforeTest(alwaysRun = true)
    public void setUp() {

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        System.out.println("Rozpoczynam działanie");
        System.setProperty("webdriver.chrome.driver", Utlis.CHROME_DRIVER_LOCATION);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test(testName = "xxx")
    public void LogIn() throws InterruptedException, IOException {
        ReadDataLogin readDataLogin = new ReadDataLogin("/home/tomasz/IdeaProjects/data.txt");
        readDataLogin.readingTXT();
        String LOGIN = readDataLogin.getLogin();
        String PASSWORD = readDataLogin.getPassword();

        driver.get(Utlis.BASE_URL);
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        WebFormLoginPage webFormLoginPage = new WebFormLoginPage(driver);
        Assert.assertTrue(webFormLoginPage.isCookiesMessageVisible());
        webFormLoginPage.clickAcceptCookiesButton();
        webFormLoginPage.inputLogin(LOGIN);
        webFormLoginPage.inputPassword(PASSWORD);
        webFormLoginPage.clickLogInButton();
        webFormLoginPage.clickMarkPlacButton();

        AddAdvert addAdvertObject = AddAdvert.getInstanceAddAdvert(driver, "First");
        addAdvertObject.clickAddNewAdvert();
        addAdvertObject.clickProductForSell();
        Assert.assertTrue(addAdvertObject.isHeaderVisible());
        addAdvertObject.uploadPhoto(Utlis.PATH_DIRECTORY);

        addAdvertObject.inputTitle("Test");
        addAdvertObject.inputPrice("500");
        addAdvertObject.inputCategory();
        addAdvertObject.inputConditionOfItem(1);
        addAdvertObject.inputDescription("ŚWIETNE SUPER");
        addAdvertObject.inputLocation("Doruchów");
        addAdvertObject.goNextClick();
//        addAdvertObject.publicAdClick();
        Thread.sleep(10000);

    }

    @AfterSuite
    public static void cleanUp(){
        driver.close();
        driver.quit();
    }
}
