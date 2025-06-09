package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FaqAccordionTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");

        mainPage = new MainPage(driver);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    @DisplayName("Проверка раскрытия ответа на вопрос по индексу и проверка текста ответа")
    public void testFaqAccordion(int index) throws InterruptedException {
        mainPage.clickQuestion(index);

        Thread.sleep(500);

        assertTrue(mainPage.isQuestionExpanded(index),
                "Вопрос по индексу " + index + " должен быть раскрыт (aria-expanded=true).");

        String panelId = mainPage.getControlledPanelId(index);

        assertTrue(mainPage.isPanelVisibleById(panelId),
                "Панель ответа для вопроса " + index + " должна быть видимой.");

        String actualText = mainPage.getPanelTextById(panelId);
        String expectedText = mainPage.getExpectedAnswerText(index);
        assertEquals(expectedText, actualText, "Текст ответа по индексу " + index + " не совпадает с ожидаемым.");

        for (int i = 0; i < 8; i++) {
            if (i != index) {
                assertFalse(mainPage.isQuestionExpanded(i),
                        "Вопрос по индексу " + i + " должен быть закрыт (aria-expanded=false).");

                String otherPanelId = mainPage.getControlledPanelId(i);
                assertFalse(mainPage.isPanelVisibleById(otherPanelId),
                        "Панель ответа для вопроса " + i + " должна быть скрыта.");
            }
        }
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
    }
}