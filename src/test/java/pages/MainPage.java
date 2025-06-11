package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public By faqSectionTitle = By.xpath("//div[contains(text(),'Вопросы о важном')]");
    public By faqQuestions = By.cssSelector("div.accordion__button");
    public By faqAnswers = By.cssSelector("div.accordion__panel");

    private By orderButtonTop = By.cssSelector("button.Button_Button__ra12g");
    private By orderButtonBottom = By.xpath("//button[text()='Заказать']");

    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }

    public void clickOrderButtonBottom() {
        driver.findElement(orderButtonBottom).click();
    }

    public WebElement getQuestion(int index) {
        return driver.findElements(faqQuestions).get(index);
    }

    public WebElement getAnswer(int index) {
        return driver.findElements(faqAnswers).get(index);
    }

    public void scrollToQuestion(int index) {
        WebElement question = getQuestion(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);
    }

    public void clickQuestion(int index) {
        scrollToQuestion(index);
        getQuestion(index).click();
    }

    public boolean isAnswerVisible(int index) {
        WebElement answer = getAnswer(index);
        String hiddenAttr = answer.getDomAttribute("hidden");
        return (hiddenAttr == null || hiddenAttr.equals("false")) && answer.isDisplayed();
    }

    public boolean isQuestionExpanded(int index) {
        String expanded = getQuestion(index).getDomAttribute("aria-expanded");
        return "true".equals(expanded);
    }

    public String getControlledPanelId(int index) {
        return getQuestion(index).getDomAttribute("aria-controls");
    }

    public boolean isPanelVisibleById(String panelId) {
        WebElement panel = driver.findElement(By.id(panelId));
        String hiddenAttr = panel.getDomAttribute("hidden");
        return (hiddenAttr == null || hiddenAttr.equals("false")) && panel.isDisplayed();
    }

    public String getPanelTextById(String panelId) {
        WebElement panel = driver.findElement(By.id(panelId));
        return panel.getText().trim();
    }

    public String getExpectedAnswerText(int index) {
        switch (index) {
            case 0:
                return "Сутки — 400 рублей. Оплата курьеру — наличными или картой.";
            case 1:
                return "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.";
            case 2:
                return "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.";
            case 3:
                return "Только начиная с завтрашнего дня. Но скоро станем расторопнее.";
            case 4:
                return "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.";
            case 5:
                return "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.";
            case 6:
                return "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.";
            case 7:
                return "Да, обязательно. Всем самокатов! И Москве, и Московской области.";
            default:
                return "";
        }
    }
}