package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderPage {
    private final WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By nameInput = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameInput = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroInput = By.className("select-search__input");
    private final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");

    private final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodDropdown = By.className("Dropdown-control");
    private final By blackColorCheckbox = By.id("black");
    private final By greyColorCheckbox = By.id("grey");
    private final By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[text()='Заказать']");
    private final By confirmYesButton = By.xpath("html/body/div/div/div[2]/div[3]/button[2]");
    private final By successPopup = By.className("Order_ModalHeader__3FDaJ");

    public void fillOrderForm(String name, String surname, String address, String metro, String phone) {
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(metroInput).sendKeys(metro);
        driver.findElement(metroInput).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        driver.findElement(phoneInput).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void fillRentalForm(String date, String rentalPeriod, boolean black, boolean grey, String comment) {
        WebElement dateField = driver.findElement(dateInput);
        dateField.click();
        dateField.clear();
        dateField.sendKeys(date);

        driver.findElement(By.tagName("body")).click();

        driver.findElement(rentalPeriodDropdown).click();
        driver.findElement(By.xpath("//div[@class='Dropdown-menu']/div[text()='" + rentalPeriod + "']")).click();

        if (black) {
            driver.findElement(blackColorCheckbox).click();
        }
        if (grey) {
            driver.findElement(greyColorCheckbox).click();
        }

        driver.findElement(commentInput).sendKeys(comment);
    }


    public void confirmOrder() {
        driver.findElement(orderButton).click();
        driver.findElement(confirmYesButton).click();
    }

    public boolean isOrderSuccessPopupDisplayed() {
        return driver.findElement(successPopup).isDisplayed();
    }
}