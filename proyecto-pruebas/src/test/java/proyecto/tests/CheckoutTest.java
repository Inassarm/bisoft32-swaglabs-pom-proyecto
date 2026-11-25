package proyecto.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import proyecto.base.BaseTest;
import proyecto.pages.CartPage;
import proyecto.pages.CheckoutPage;
import proyecto.pages.InventoryPage;
import proyecto.pages.LoginPage;

class CheckoutTest extends BaseTest {

    @Test
    @DisplayName("TC25 Checkout con apellido vacío muestra error de validación")
    void tc25_checkoutMissingLastName() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        new InventoryPage(driver).addFirstProduct();

        CartPage cart = new CartPage(driver);
        cart.open();
        cart.proceedToCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.clickContinueExpectingError("QA", "", "10101");
        assertTrue(checkout.getErrorText().contains("Last Name is required"));
    }

    @Test
    @DisplayName("TC26 Checkout con código postal vacío muestra error de validación")
    void tc26_checkoutMissingPostalCode() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        new InventoryPage(driver).addFirstProduct();

        CartPage cart = new CartPage(driver);
        cart.open();
        cart.proceedToCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.clickContinueExpectingError("QA", "Tester", "");
        assertTrue(checkout.getErrorText().contains("Postal Code is required"));
    }

    @Test
    @DisplayName("TC27 Flujo completo de compra exitoso con confirmación final")
    void tc27_completeCheckout() {
        new LoginPage(driver).login("standard_user", "secret_sauce");

        InventoryPage inventory = new InventoryPage(driver);
        assertEquals("Products", inventory.getTitleText());
        inventory.addFirstProduct();

        CartPage cart = new CartPage(driver);
        cart.open();
        assertEquals(1, cart.itemCount());
        cart.proceedToCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillInformation("QA", "Tester", "10101");
        checkout.finish();
        assertTrue(checkout.confirmation().toLowerCase().contains("thank"));
    }
}