package pageobjects;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Random random = new Random();

    public static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    public static final Duration TIMEOUT = Duration.ofSeconds(10);

    // Локаторы для элементов страницы
    private By inputName = By.xpath(".//input[@placeholder= '* Имя']");
    private By inputSurname = By.xpath(".//input[@placeholder= '* Фамилия']");
    private By inputAddress = By.xpath(".//input[@placeholder= '* Адрес: куда привезти заказ']");
    private By stationDropdown = By.className("select-search__input");
    private By stationOptions = By.className("select-search__select");
    private By inputPhone = By.xpath(".//input[@placeholder= '* Телефон: на него позвонит курьер']");
    private By orderButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    private By orderHeaderText = By.className("Order_Header__BZXOb");
    private By calendarInput = By.xpath(".//input[@placeholder= '* Когда привезти самокат']");
    private By calendarContainer = By.className("react-datepicker__month-container");
    private By availableDates = By.xpath("//div[contains(@class, 'react-datepicker__month-container')]");
    private By scooterDropdown = By.className("Dropdown-placeholder");
    private By scooterDropdownMenu = By.className("Dropdown-menu");
    private By colorBlack = By.id("black");
    private By commentInput = By.xpath(".//input[@placeholder= 'Комментарий для курьера']");
    private By confirmOrderButton = By.xpath(".//div[@class = 'Order_Buttons__1xGrp']//button[text()='Заказать']");
    private By orderModal = By.className("Order_Modal__YZ-d3");
    private By orderModalHeader = By.cssSelector(".Order_ModalHeader__3FDaJ");
    private By confirmYesButton = By.xpath(".//div[@class = 'Order_Buttons__1xGrp']/button[text() = 'Да']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    public void open() {
        driver.get(PAGE_URL);  // Открытие страницы
    }

    public void placeOrder(By orderButtonLocator, String name, String surname, String address, String phone, String comment) {
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(orderButtonLocator));
        orderButton.click();
        wait.until(ExpectedConditions.urlContains("/order"));
        Assert.assertTrue("Перенаправление не произошло!", driver.getCurrentUrl().startsWith("https://qa-scooter.praktikum-services.ru/order"));

        driver.findElement(inputName).sendKeys(name);
        driver.findElement(inputSurname).sendKeys(surname);
        driver.findElement(inputAddress).sendKeys(address);

        driver.findElement(stationDropdown).click();
        List<WebElement> options = driver.findElements(stationOptions);
        if (!options.isEmpty()) {
            options.get(random.nextInt(options.size())).click();
        }

        driver.findElement(inputPhone).sendKeys(phone);
        driver.findElement(orderButtonLocator).click();  // Используем переданный локатор

        WebElement headerText = wait.until(ExpectedConditions.visibilityOfElementLocated(orderHeaderText));
        Assert.assertTrue("Текст заголовка не соответствует ожиданиям", headerText.getText().contains("Про аренду"));

        driver.findElement(calendarInput).click();
        Assert.assertTrue("Календарь не открыт", driver.findElement(calendarContainer).isDisplayed());

        List<WebElement> availableDatesList = driver.findElements(availableDates);
        if (!availableDatesList.isEmpty()) {
            availableDatesList.get(random.nextInt(availableDatesList.size())).click();
        } else {
            throw new RuntimeException("Нет доступных дат в календаре!");
        }

        driver.findElement(scooterDropdown).click();
        Assert.assertTrue("Селектор не открыт", driver.findElement(scooterDropdownMenu).isDisplayed());

        List<WebElement> availableScooters = driver.findElements(scooterDropdownMenu);
        if (!availableScooters.isEmpty()) {
            availableScooters.get(random.nextInt(availableScooters.size())).click();
        }

        driver.findElement(colorBlack).click();
        driver.findElement(commentInput).sendKeys(comment);
        driver.findElement(confirmOrderButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(orderModal));
        WebElement modalHeader1 = wait.until(ExpectedConditions.visibilityOfElementLocated(orderModalHeader));
        Assert.assertEquals("Хотите оформить заказ?", modalHeader1.getText().trim());

        driver.findElement(confirmYesButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(orderModal));
        WebElement modalHeader2 = wait.until(ExpectedConditions.visibilityOfElementLocated(orderModalHeader));
        Assert.assertEquals("Заказ оформлен", modalHeader2.getText().trim());
    }
}
