package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.OrderPage;
import java.time.Duration;

public class OrderTest {
    private WebDriver driver;
    private OrderPage orderPage;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        orderPage = new OrderPage(driver);
        orderPage.open();  // Открываем страницу заказа
    }

    @Test
    public void orderPlacementFromTopButton() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        WebElement orderButtonTop = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(".//div[@class = 'Header_Nav__AGCXC']/button[@class = 'Button_Button__ra12g']")
        ));

        // Помещаем заказ через верхнюю кнопку
        orderPage.placeOrder(By.xpath(".//div[@class = 'Header_Nav__AGCXC']/button[@class = 'Button_Button__ra12g']"),
                "Николай", "Смирнов", "Яхтенная", "89112898995", "Звонить после 10 утра и до 18 вечера");
    }

    @Test
    public void testOrderFromMiddleButton() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        WebElement orderButtonMiddle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM")
        ));

        // Прокручиваем до средней кнопки и размещаем заказ
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", orderButtonMiddle);
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonMiddle));

        orderPage.placeOrder(By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM"),
                "Анна", "Иванова", "Ленина 15", "89035556677", "Оставить у двери");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Закрытие драйвера
        }
    }
}

