package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.NavigationPage;

public class NavigationTest {
    private WebDriver driver;
    private NavigationPage navigationPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        navigationPage = new NavigationPage(driver);
        navigationPage.open();
    }

    @Test
    public void testLogoScooterYandexPageOpenNewWindow() {
        navigationPage.clickYandexLogo();
        Assert.assertTrue("Страница Яндекса не открылась!", navigationPage.isYandexPageOpened());
    }

    @Test
    public void testClickLogoScooterMain() {
        navigationPage.clickOrderButton();
        Assert.assertTrue("Перенаправление на страницу заказа не произошло!", driver.getCurrentUrl().contains("/order"));

        navigationPage.clickScooterLogo();
        Assert.assertTrue("Перенаправление на главную страницу не произошло!", navigationPage.isOnMainPage());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер после каждого теста
        }
    }
}
