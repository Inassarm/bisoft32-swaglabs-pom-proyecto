package proyecto.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import proyecto.base.BaseTest;
import proyecto.pages.CartPage;
import proyecto.pages.InventoryPage;
import proyecto.pages.LoginPage;

class CartTest extends BaseTest {

    @Test
    @DisplayName("TC20 Abrir carrito vacío sin productos")
    void tc20_openEmptyCart() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        CartPage cart = new CartPage(driver);
        cart.open();
        assertEquals(0, cart.itemCount());
    }

    @Test
    @DisplayName("TC21 Verificar producto agregado dentro de página Carrito")
    void tc21_cartShowsAddedProduct() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        InventoryPage inventory = new InventoryPage(driver);
        assertEquals("Products", inventory.getTitleText());

        inventory.addFirstProduct();
        assertEquals("1", inventory.cartCount());

        CartPage cart = new CartPage(driver);
        cart.open();
        assertEquals(1, cart.itemCount());
    }

    @Test
    @DisplayName("TC22 Retirar producto desde la vista del carrito")
    void tc22_removeProductFromCart() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        InventoryPage inventory = new InventoryPage(driver);
        inventory.addFirstProduct();

        CartPage cart = new CartPage(driver);
        cart.open();
        assertEquals(1, cart.itemCount());

        cart.removeFirstItem();
        assertEquals(0, cart.itemCount());
    }
}