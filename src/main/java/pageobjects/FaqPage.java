package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class FaqPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    public static final Duration TIMEOUT = Duration.ofSeconds(10);

    public FaqPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    // Открытие страницы
    public void open() {
        driver.get(PAGE_URL);
    }

    // Получить список заголовков аккордеона
    public List<WebElement> getAccordionHeaders() {
        return driver.findElements(By.className("accordion__heading"));
    }

    // Ожидание и клик по заголовку аккордеона
    public void clickAccordionHeader(int index) {
        List<WebElement> headers = getAccordionHeaders();
        if (index < headers.size()) {
            WebElement header = headers.get(index);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", header);
            header.click();
        } else {
            throw new IndexOutOfBoundsException("Неверный индекс заголовка аккордеона: " + index);
        }
    }

    // Получить текст открытого элемента аккордеона
    public String getAccordionText(int index) {
        String panelId = "accordion__panel-" + index;
        WebElement panelTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(panelId)));
        return panelTextElement.getText();
    }
}