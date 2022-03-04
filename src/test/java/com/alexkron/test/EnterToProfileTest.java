package com.alexkron.test;

import com.alexkron.page.BookStorePage;
import com.alexkron.page.LoginPage;
import com.alexkron.page.ProfilePage;
import com.alexkron.page.impl.BookStorePageImpl;
import com.alexkron.page.impl.LoginPageImpl;
import com.alexkron.page.impl.ProfilePageImpl;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class EnterToProfileTest {
    private WebDriver webDriver;
    private BookStorePage bookStorePage;
    private LoginPage loginPage;
    private ProfilePage profilePage;

    @BeforeSuite(alwaysRun = true)
    @Parameters({"driverPath"})
    public void setDriver(String driverPath) {
        System.setProperty("webdriver.chrome.driver", driverPath);
    }

    @BeforeClass(alwaysRun = true)
    @Parameters({"bookStoreUri"})
    public void setWindow(String bookStoreUrl) {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(bookStoreUrl);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        bookStorePage = new BookStorePageImpl();
        bookStorePage.init(webDriver);
        loginPage = new LoginPageImpl();
        loginPage.init(webDriver);
        profilePage = new ProfilePageImpl();
        profilePage.init(webDriver);
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        webDriver.quit();
    }

    @AfterMethod(onlyForGroups = {"acceptLogin"}, alwaysRun = true)
    @Parameters({"loginUri"})
    public void logOut(String loginUri) {
        webDriver.get(loginUri);
        loginPage.logout();
    }

    @AfterMethod(onlyForGroups = {"login"}, alwaysRun = true)
    @Parameters({"bookStoreUri"})
    public void toBookStore(String bookStoreUri) {
        webDriver.get(bookStoreUri);
    }


    @Test(groups = {"login", "acceptLogin"}, priority = 0)
    @Description("Login through the button on the page Book Store")
    @Parameters({"registeredAccountLogin", "registeredAccountPassword", "bookStoreUri"})
    public void loginFromBookStoreLoginButtonTest(String login, String password, String bookStoreUri) throws InterruptedException {
        bookStorePage.loginButtonClick();
        loginPage.login(login, password, bookStoreUri);
        Assert.assertEquals(webDriver.getCurrentUrl().toLowerCase(), bookStoreUri.toLowerCase());
        Assert.assertEquals(bookStorePage.getCurrentUserName(), login);
    }

    @Test(groups = {"login", "acceptLogin"}, priority = 0)
    @Description("Login through the Login page to access the Profile")
    @Parameters({"registeredAccountLogin", "registeredAccountPassword", "profileUri", "loginUri"})
    public void loginToProfile(String login, String password, String profileUri, String loginUri) {
        webDriver.get(loginUri);
        loginPage.login(login, password, profileUri);
        Assert.assertEquals(profilePage.getUserName(), login);
        Assert.assertEquals(webDriver.getCurrentUrl(), profileUri);
    }

    @Test(groups = {"login", "rejectLogin"}, priority = 1)
    @Description("Checking an unauthorized user's access to a Profile")
    @Parameters({"profileUri"})
    public void checkProfileWithoutLogin(String profileUri) {
        webDriver.get(profileUri);
        String notLogginMessage = "Currently you are not logged into the Book Store application, please visit the login page to enter or register page to register yourself.";
        Assert.assertEquals(profilePage.getNotLoggedMessage(), notLogginMessage);
    }


}
