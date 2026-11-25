package proyecto.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import proyecto.base.BaseTest;
import proyecto.pages.InventoryPage;
import proyecto.pages.LoginPage;

class LoginTest extends BaseTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/login_cases.csv", numLinesToSkip = 1)
    @DisplayName("TC01-TC08 Escenarios parametrizados de inicio de sesión")
    void loginCases(String username, String password, String expected) {
        String user = username == null ? "" : username;
        String pass = password == null ? "" : password;

        LoginPage login = new LoginPage(driver);
        login.login(user, pass);

        switch (expected) {
            case "success" -> assertEquals("Products", new InventoryPage(driver).getTitleText());
            case "locked" -> assertTrue(login.errorMessage().toLowerCase().contains("locked"));
            case "username_required" -> assertTrue(login.errorMessage().contains("Username is required"));
            case "password_required" -> assertTrue(login.errorMessage().contains("Password is required"));
            default -> assertFalse(login.errorMessage().isBlank());
        }
    }
}