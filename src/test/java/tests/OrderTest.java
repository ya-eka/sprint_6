package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.MainPage;
import pages.OrderPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTest extends BaseTest {

    static Object[][] testData() {
        return new Object[][]{
                {"Иван", "Иванов", "Москва, ул. Ленина, д.1", "Библиотека имени Ленина", "+79270000000"},
                {"Мария", "Петрова", "Санкт-Петербург, Невский проспект, д.10", "Площадь Гагарина", "+79271111111"}
        };
    }

    @ParameterizedTest
    @MethodSource("testData")
    public void orderScooterViaTopButton(String name, String surname, String address, String metro, String phone) {
        makeOrder(name, surname, address, metro, phone, true);
    }

    @ParameterizedTest
    @MethodSource("testData")
    public void orderScooterViaBottomButton(String name, String surname, String address, String metro, String phone) {
        makeOrder(name, surname, address, metro, phone, false);
    }

    private void makeOrder(String name, String surname, String address, String metro, String phone, boolean fromTopButton) {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        if (fromTopButton) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        orderPage.fillOrderForm(name, surname, address, metro, phone);
        orderPage.clickNextButton();

        orderPage.fillRentalForm(
                "10.06.2025",
                "двое суток",
                true,
                false,
                "Позвоните за 30 минут"
        );

        orderPage.confirmOrder();

        assertTrue(orderPage.isOrderSuccessPopupDisplayed(), "Окно успешного заказа не появилось");
    }
}