package com.srm.tests;

import com.srm.config.ConfigReader;
import com.srm.pages.AuthLoginPage;
import com.srm.pages.SecureAreaPage;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AuthTest extends BaseTest {

    @DataProvider(name = "loginCredentials")
    public Object[][] loginCredentials() {
        return new Object[][]{
                {ConfigReader.getLoginUsername(), ConfigReader.getLoginPassword(), true, "You logged into a secure area!"},
                {ConfigReader.getLoginUsername(), "WrongPassword", true, "Your password is invalid!"},
                {"", "", true, "Your username is invalid!"}
        };
    }

    @Test(dataProvider = "loginCredentials")
    public void verifyLoginScenarios(String username, String password, boolean shouldSucceed, String expectedMessage) {
        AuthLoginPage loginPage = new AuthLoginPage(driver).open();

        if (shouldSucceed) {
            SecureAreaPage secureAreaPage = loginPage.loginWithValidUser(username, password);
            Assert.assertTrue(secureAreaPage.isSecurePageLoaded(), "Secure area page should load after valid login.");
            Assert.assertTrue(secureAreaPage.getSuccessMessage().contains(expectedMessage),
                    "Success message should match the expected text.");
        } else {
            loginPage.login(username, password);
            Assert.assertTrue(loginPage.getFlashMessage().contains(expectedMessage),
                    "Flash message should match the expected failure text.");
        } 
    }

    @Test
    public void verifyLogoutEndsSession() {
        AuthLoginPage loginPage = new AuthLoginPage(driver).open();
        SecureAreaPage secureAreaPage =
                loginPage.loginWithValidUser(ConfigReader.getLoginUsername(), ConfigReader.getLoginPassword());

        AuthLoginPage returnedLoginPage = secureAreaPage.logout();
        Assert.assertTrue(returnedLoginPage.isLoaded()
                        || returnedLoginPage.getFlashMessage().contains("You logged out of the secure area!"),
                "Logout should return the user to the login page.");
    }
}