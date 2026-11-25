package proyecto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.cssSelector("[data-test='continue']");
    private final By finishButton = By.cssSelector("[data-test='finish']");
    private final By completeHeader = By.className("complete-header");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillInformation(String first, String last, String postal) {
        WebElement firstEl = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        firstEl.clear();
        firstEl.sendKeys(first);

        WebElement lastEl = wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        lastEl.clear();
        lastEl.sendKeys(last);

        WebElement postalEl = wait.until(ExpectedConditions.visibilityOfElementLocated(postalCode));
        postalEl.clear();
        postalEl.sendKeys(postal);

        WebElement contBtn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", contBtn);
        contBtn.click();
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public void clickContinueExpectingError(String first, String last, String postal) {
        WebElement firstEl = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        firstEl.clear();
        firstEl.sendKeys(first);

        WebElement lastEl = wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        lastEl.clear();
        lastEl.sendKeys(last);

        WebElement postalEl = wait.until(ExpectedConditions.visibilityOfElementLocated(postalCode));
        postalEl.clear();
        postalEl.sendKeys(postal);

        WebElement contBtn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", contBtn);
        contBtn.click();
    }

    public String getErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public void finish() {
        WebElement finBtn = wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", finBtn);
        finBtn.click();
    }

    public String confirmation() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader)).getText();
    }
}