package proyecto.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import proyecto.base.BaseTest;
import proyecto.pages.InventoryPage;
import proyecto.pages.LoginPage;

class InventoryTest extends BaseTest {

    @Test
    @DisplayName("TC10 Acceso a detalle de producto y coincidencia de datos")
    void tc10_openProductDetail() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        InventoryPage inventory = new InventoryPage(driver);
        String selectedName = inventory.openFirstProductDetail();
        assertEquals(selectedName, inventory.getDetailName());
    }

    @Test
    @DisplayName("TC12 Ordenar inventario por Nombre (A a Z)")
    void tc12_sortByNameAsc() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("az");

        List<String> actual = inventory.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(String::compareTo);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("TC13 Ordenar inventario por Nombre (Z a A)")
    void tc13_sortByNameDesc() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("za");

        List<String> actual = inventory.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Comparator.reverseOrder());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("TC14 Ordenar inventario por Precio (menor a mayor)")
    void tc14_sortByPriceAsc() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("lohi");

        List<Double> actual = inventory.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Double::compareTo);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("TC20 Retirar producto desde inventario actualiza el contador")
    void tc20_removeProductFromInventory() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        InventoryPage inventory = new InventoryPage(driver);
        inventory.addFirstProduct();
        assertEquals("1", inventory.cartCount());

        inventory.removeFirstProduct();
        assertTrue(inventory.isCartBadgeHidden());
    }
    @Test
    @DisplayName("TC15 Ordenar inventario por Precio (mayor a menor)")
    void tc15_sortByPriceDesc() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("hilo");

        List<Double> actual = inventory.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Comparator.reverseOrder());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("TC29 Cierre de sesión desde el menú lateral retorna al login")
    void tc29_logoutReturnsToLogin() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        InventoryPage inventory = new InventoryPage(driver);
        assertEquals("Products", inventory.getTitleText());

        inventory.logout();
        assertTrue(login.isLoginVisible());
    }
}

