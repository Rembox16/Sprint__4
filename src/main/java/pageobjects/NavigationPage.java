package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Set;

public class NavigationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    public static final Duration TIMEOUT = Duration.ofSeconds(10);

    private By yandexLogo = By.cssSelector(".Header_LogoYandex__3TSOI");
    private By orderButtonTop = By.xpath(".//div[@class = 'Header_Nav__AGCXC']/button[@class = 'Button_Button__ra12g']");
    private By scooterLogo = By.xpath(".//img[@alt='Scooter']");

    public NavigationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    // Открытие страницы
    public void open() {
        driver.get(PAGE_URL);
    }

    // Клик по логотипу Яндекса и переключение на новое окно
    public void clickYandexLogo() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(yandexLogo)).click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Переключение на новое окно
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);
        }
    }

    // Ожидание загрузки страницы Яндекса
    public boolean isYandexPageOpened() {
        return wait.until(ExpectedConditions.urlToBe("https://dzen.ru/?yredirect=true"));
    }

    // Клик по кнопке "Заказать" и проверка редиректа на страницу заказа
    public void clickOrderButton() {
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        orderButton.click();
        wait.until(ExpectedConditions.urlContains("/order"));
    }

    // Клик по логотипу Самоката
    public void clickScooterLogo() {
        driver.findElement(scooterLogo).click();
    }

    // Проверка, что текущий URL — главная страница
    public boolean isOnMainPage() {
        return driver.getCurrentUrl().startsWith(PAGE_URL);
    }
}