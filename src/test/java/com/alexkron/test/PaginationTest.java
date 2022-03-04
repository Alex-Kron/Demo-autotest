package com.alexkron.test;

import com.alexkron.page.BookStorePage;
import com.alexkron.page.impl.BookStorePageImpl;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class PaginationTest {
    private WebDriver webDriver;
    private BookStorePage bookStorePage;

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
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        webDriver.quit();
    }

    @BeforeGroups("buttons")
    public void setMinimalRows() {
        List<String> options = bookStorePage.getRowPerPageOptions();
        bookStorePage.setRowsPerPage(options.get(0));
    }

    @BeforeMethod(onlyForGroups = {"buttons"})
    public void setFirstPage() {
        bookStorePage.setFirstPage();
    }

    @Test(groups = "pagination", priority = 0)
    @Description("Check if the number of rows on the page Book Store matches the selected option")
    public void rowCountPerPageTest() {
        List<String> options = bookStorePage.getRowPerPageOptions();
        for (String option : options) {
            Integer count = bookStorePage.setRowsPerPage(option);
            Assert.assertEquals(count.toString(), option);
        }
    }

    @Test(groups = "pagination", priority = 2)
    @Description("Select multiply check")
    public void selectRowPerPageMultipleTest() {
        Assert.assertFalse(bookStorePage.isSelectRowsPerPageMultiple());
    }

    @Test(groups = {"pagination", "buttons"}, priority = 1)
    @Description("Checking that for the first page it is impossible to click on the previous one")
    public void previousDisableTest() {
        Assert.assertFalse(bookStorePage.isPreviousPageEnable());
    }

    @Test(groups = {"pagination", "buttons"}, priority = 1)
    @Description("Checking that for the last page it is impossible to click on the next one")
    public void nextDisableTest() {
        bookStorePage.setMaxPage();
        Assert.assertFalse(bookStorePage.isNextPageEnable());
    }

    @Test(groups = {"pagination", "buttons"}, priority = 1)
    @Description("Checking page clicks back")
    public void previousClickTest() {
        bookStorePage.setMaxPage();
        while (bookStorePage.getPageNumber() > 1) {
            Assert.assertTrue(bookStorePage.isPreviousPageEnable());
            bookStorePage.toPreviousPage();
        }
    }

    @Test(groups = {"pagination", "buttons"}, priority = 1)
    @Description("Checking page forward clicks")
    public void nextClickTest() {
        Integer max = bookStorePage.getTotalPages();
        while (bookStorePage.getPageNumber() < max) {
            Assert.assertTrue(bookStorePage.isNextPageEnable());
            bookStorePage.toNextPage();
        }
    }
}
