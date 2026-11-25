package proyecto.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage extends BasePage {
    private final By title = By.cssSelector(".title");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By firstAddButton = By.cssSelector("[data-test^='add-to-cart']");
    private final By firstRemoveButton = By.cssSelector("[data-test^='remove']");
    private final By sortSelect = By.cssSelector("[data-test='product-sort-container']");
    private final By itemNames = By.cssSelector(".inventory_item_name");
    private final By itemPrices = By.cssSelector(".inventory_item_price");
    private final By firstItemTitle = By.cssSelector(".inventory_item_name");
    private final By detailName = By.cssSelector(".inventory_details_name");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    public void addFirstProduct() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(firstAddButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }
    }

    public void removeFirstProduct() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(firstRemoveButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }
    }

    public String cartCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public boolean isCartBadgeHidden() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
        } catch (TimeoutException e) {
            WebElement retryBtn = driver.findElement(firstRemoveButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", retryBtn);
            return new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
        }
    }

    public void selectSortOption(String value) {
        Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(sortSelect)));
        select.selectByValue(value);
    }

    public List<String> getProductNames() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(itemNames))
                .stream().map(e -> e.getText().trim()).toList();
    }

    public List<Double> getProductPrices() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(itemPrices))
                .stream().map(e -> Double.parseDouble(e.getText().replace("$", "").trim())).toList();
    }

    public String openFirstProductDetail() {
        String name = wait.until(ExpectedConditions.visibilityOfElementLocated(firstItemTitle)).getText().trim();
        wait.until(ExpectedConditions.elementToBeClickable(firstItemTitle)).click();
        return name;
    }

    public String getDetailName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(detailName)).getText().trim();
    }
}