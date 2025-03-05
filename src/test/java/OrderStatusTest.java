package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.OrderStatusPage;
import static org.junit.Assert.assertTrue;

public class OrderStatusTest {
    private WebDriver driver;
    private OrderStatusPage orderStatusPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        orderStatusPage = new OrderStatusPage(driver);
        orderStatusPage.open();
    }

    @Test
    public void testOrderStatusPage() {
        orderStatusPage.clickOrderStatus();
        orderStatusPage.enterOrderNumber("Самокатт");
        orderStatusPage.clickGoButton();

        assertTrue("Картинка 'Заказ не найден' не отображается!", orderStatusPage.isOrderNotFoundDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер после каждого теста
        }
    }
}
