package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FaqAccordionTest extends BaseTest {
    private MainPage mainPage;
    private WebDriverWait wait;

    @BeforeAll
    public void init() {
        mainPage = new MainPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    @DisplayName("Проверка раскрытия ответа на вопрос по индексу и проверка текста ответа")
    public void testFaqAccordion(int index) {
        mainPage.clickQuestion(index);

        wait.until((ExpectedCondition<Boolean>) d -> mainPage.isQuestionExpanded(index));

        assertTrue(mainPage.isQuestionExpanded(index),
                "Вопрос по индексу " + index + " должен быть раскрыт (aria-expanded=true).");

        String panelId = mainPage.getControlledPanelId(index);

        assertTrue(mainPage.isPanelVisibleById(panelId),
                "Панель ответа для вопроса " + index + " должна быть видимой.");

        String actualText = mainPage.getPanelTextById(panelId);
        String expectedText = mainPage.getExpectedAnswerText(index);
        assertEquals(expectedText, actualText, "Текст ответа по индексу " + index + " не совпадает с ожидаемым.");
    }
}