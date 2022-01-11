import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddAdvert extends PageObject {
    WebDriverWait wait = new WebDriverWait(driver, 15);
    FilePage filePage = new FilePage();
    JavascriptExecutor js = (JavascriptExecutor) driver;

    private static AddAdvert addAdvertInstance;
    public String value;


    private AddAdvert() {
    }

    private AddAdvert(WebDriver driver, String value) {
        super(driver);
        this.value = value;

    }

    public static AddAdvert getInstanceAddAdvert(WebDriver driver, String value) {
        if (addAdvertInstance == null) {
            addAdvertInstance = new AddAdvert(driver, value);
        }
        return addAdvertInstance;
    }

    /* Dodanie ogłoszenia */
    @FindBy(xpath = "//a[contains(@aria-label,'Utwórz nowe ogłoszenie')] | //a[contains(@href,'/marketplace/create')]/descendant::i")
    WebElement addNewAdvert;

    // Przedmiot na sprzedaż
    @FindBy(xpath = "//span[contains(text(),'Przedmiot na sprzedaż')]//ancestor-or-self::a | //span[contains(text(),'Item for Sale')]")
    WebElement productForSell;

    // Header: Przedmiot na sprzedaż
    @FindBy(xpath = "//h1[text() = 'Przedmiot na sprzedaż']")
    WebElement headerProductForSell;

    // Lokalizacja dropdownList
    @FindBy(xpath = "//*[text()='Doruchów']")
    WebElement cityLocalization;

    // Stan dropdownList
    @FindBy(xpath = "//label[@aria-label='Stan']//child::span[1]//following-sibling::div")
    WebElement conditionClick;

    // Lista elementow stanu
    @FindBy(xpath = "//div[@role='menu']")
    WebElement menuOfElementCondition;

    // Kategoria dropDownList
    @FindBy(xpath = "//*[text()='Meble']//parent::div")
    WebElement furniture;

    //Dodane zdjęcie
    @FindBy(xpath = "//div[@aria-label='Marketplace']//img")
    List<WebElement> photosAvatar;

    // Ładowanie elemntu
    @FindBy(xpath = "//div[@aria-label='Marketplace']//div[@aria-valuetext='Ładowanie...']")
    WebElement loadingPhoto;

    // Header Dodawania zdjęcia
    @FindBy(xpath = "//div[@aria-label='Marketplace']//*[contains(text(),'Dodaj zdjęci')]")
    WebElement addPhotoIsVisible;

    // Dalej
    @FindBy(xpath = "//div[@aria-label='Dalej']")
    WebElement goNext;

    // Opublikuj
    @FindBy(xpath = "//div[@aria-label='Opublikuj']")
    WebElement publicAd;

    /*Dynamiczne wypełnianie pól do ogłoszenia */
    // Wprowadz tytuł
    public WebElement dynamicDataForAdvert(String variable) {
        String xPath = "";
        switch (variable) {
            case "Tytuł":
            case "Cena":
            case "Lokalizacja":
                xPath = "//span[text() ='" + variable + "']//following-sibling::input";
                break;
            case "Kategoria":
            case "Dostępność":
            case "Znaczniki produktu":
                xPath = "//span[text() ='" + variable + "']//following-sibling::div";
                break;
            case "Opis":
                xPath = "//span[text() ='" + variable + "']//following-sibling::textarea";
                break;
            case "Dodaj zdjęcia":
                xPath = "//input[contains(@accept,'image')]";
                break;
        }
        return driver.findElement(By.xpath(xPath));
    }

    //Dynamic stan przedmiot
    /*
    1) Nowy
    2) Używany, jak nowy
    3) Używany, w dobrym stanie
    4) Używany, w przeciętnym stanie
     */
    public WebElement conditionOfItem(int condition) {
        String cond = "";
        switch (condition) {
            case 1:
                cond = "Nowy";
                break;
            case 2:
                cond = "Używany, jak nowy";
                break;
            case 3:
                cond = "Używany, w dobrym stanie";
                break;
            case 4:
                cond = "Używany, w przeciętnym stanie";
                break;
        }

        String xPath = "//*[@role='menu']//child::div[@tabindex=0]";
        return driver.findElement(By.xpath(xPath));
    }

    //Metody

    public void clickAddNewAdvert() {
        wait.until(ExpectedConditions.visibilityOf(addNewAdvert));
        addNewAdvert.click();
    }

    public void clickProductForSell() {
        wait.until(ExpectedConditions.visibilityOf(productForSell));
        productForSell.click();
    }

    public boolean isHeaderVisible() {
        return headerProductForSell.isDisplayed();
    }

    public void inputTitle(String title) {
        dynamicDataForAdvert("Tytuł").sendKeys(title);
    }

    public void inputPrice(String price) {
        dynamicDataForAdvert("Cena").sendKeys(price);
    }

    public void inputDescription(String description) {
        dynamicDataForAdvert("Opis").sendKeys(description);
    }

    public void inputLocation(String location) {
        dynamicDataForAdvert("Lokalizacja").click();
        dynamicDataForAdvert("Lokalizacja").sendKeys(location);
        Assert.assertTrue(cityLocalization.isDisplayed());
        cityLocalization.click();
    }

    //TODO pozostałe kategorie
    public void inputCategory() {
        dynamicDataForAdvert("Kategoria").click();
        Assert.assertTrue(furniture.isDisplayed());
        furniture.click();
    }

    public void inputConditionOfItem(int condition) throws InterruptedException {
        js.executeScript("arguments[0].scrollIntoView(true);", conditionClick);
        wait.until(ExpectedConditions.elementToBeClickable(conditionClick));
        conditionClick.click();
        conditionOfItem(condition).click();
    }

    public int countAmountOfUploadedFile() {
        return photosAvatar.size();
    }

    public void uploadPhoto(String pathToPhotos) throws InterruptedException {
        ArrayList<String> photosPath = filePage.toFilePathBuilder(pathToPhotos);
        for (String path : photosPath) {
            wait.until(ExpectedConditions.visibilityOf(addPhotoIsVisible));
            dynamicDataForAdvert("Dodaj zdjęcia").sendKeys(path);
            wait.until(ExpectedConditions.invisibilityOf(loadingPhoto));

        }
    }

    public void goNextClick() {
        wait.until(ExpectedConditions.elementToBeClickable(goNext));
        goNext.click();
    }

    public void publicAdClick() {
        wait.until(ExpectedConditions.elementToBeClickable(publicAd));
        publicAd.click();
    }
}
