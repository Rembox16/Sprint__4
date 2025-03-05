import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.FaqPage;

import java.util.List;
import static org.junit.Assert.assertEquals;

public class FaqTest {
    private WebDriver driver;
    private FaqPage faqPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        faqPage = new FaqPage(driver);
        faqPage.open();
    }

    @Test
    public void testAccordionTexts() {
        String[] expectedTexts = {
                "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
                "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
                "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
                "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
                "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
                "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
                "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
                "Да, обязательно. Всем самокатов! И Москве, и Московской области."
        };

        List<WebElement> headers = faqPage.getAccordionHeaders();
        assert !headers.isEmpty() : "Элементы аккордеона не найдены!";

        for (int i = 0; i < headers.size(); i++) {
            faqPage.clickAccordionHeader(i);
            String actualText = faqPage.getAccordionText(i);

            System.out.println("Ожидаемый текст: " + expectedTexts[i]);
            System.out.println("Фактический текст: " + actualText);

            assertEquals("Текст внутри аккордеона не совпадает!", expectedTexts[i], actualText);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер после теста
        }
    }
}
