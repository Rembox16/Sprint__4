package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderStatusPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    public static final Duration TIMEOUT = Duration.ofSeconds(10);

    private By orderStatusButton = By.className("Header_Link__1TAG7");
    private By orderNumberInput = By.xpath(".//input[@class = 'Input_Input__1iN_Z Header_Input__xIoUq']");
    private By goButton = By.cssSelector(".Button_Button__ra12g.Header_Button__28dPO");
    private By notFoundImg = By.cssSelector("div.Track_NotFound__6oaoY > img");

    public OrderStatusPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    // Открытие главной страницы
    public void open() {
        driver.get(PAGE_URL);
    }

    // Нажатие на кнопку "Статус заказа"
    public void clickOrderStatus() {
        driver.findElement(orderStatusButton).click();
    }

    // Ввод номера заказа
    public void enterOrderNumber(String orderNumber) {
        WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(orderNumberInput));
        inputField.sendKeys(orderNumber);
    }

    // Нажатие на кнопку "Go"
    public void clickGoButton() {
        driver.findElement(goButton).click();
    }

    // Проверка, что картинка "Заказ не найден" отображается
    public boolean isOrderNotFoundDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(notFoundImg));
        return driver.findElement(notFoundImg).isDisplayed();
    }
}