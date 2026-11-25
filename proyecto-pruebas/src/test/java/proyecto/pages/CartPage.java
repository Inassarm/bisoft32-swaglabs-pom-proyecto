package proyecto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {
    private final By cartLink = By.className("shopping_cart_link");
    private final By items = By.className("cart_item");
    private final By removeButton = By.cssSelector("[data-test^='remove']");
    private final By checkoutButton = By.cssSelector("[data-test='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(cartLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        try {
            wait.until(ExpectedConditions.urlContains("cart.html"));
        } catch (org.openqa.selenium.TimeoutException ex) {
            if (!driver.getCurrentUrl().contains("cart.html")) {
                WebElement retryLink = driver.findElement(cartLink);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", retryLink);
                wait.until(ExpectedConditions.urlContains("cart.html"));
            }
        }
    }

    public int itemCount() {
        return driver.findElements(items).size();
    }

    public void removeFirstItem() {
        int beforeCount = itemCount();
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(removeButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }
        wait.until(d -> d.findElements(items).size() < beforeCount);
    }

    public void proceedToCheckout() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }
        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-one"));
        } catch (org.openqa.selenium.TimeoutException ex) {
            if (driver.getCurrentUrl().contains("cart.html")) {
                WebElement retryBtn = driver.findElement(checkoutButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", retryBtn);
                wait.until(ExpectedConditions.urlContains("checkout-step-one"));
            }
        }
    }
}