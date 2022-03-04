package com.alexkron.test;

import com.alexkron.page.BookStorePage;
import com.alexkron.page.impl.BookStorePageImpl;
import com.alexkron.provider.SearchDataProvider;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class SearchTest {
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

    @Test(groups = {"search"})
    @Description("Checking the output of the entire list with an empty string at the input")
    public void emptySearchRequestTest() {
        List<String> allBooks = bookStorePage.getAllBooksFromPage();
        List<String> responseBooks = bookStorePage.search("");
        Assert.assertEquals(allBooks, responseBooks);
    }

    @Test(dataProvider = "bookFieldProvider", dataProviderClass = SearchDataProvider.class, groups = {"search"}, priority = 0)
    @Description("Checking the search output for different input data: full match in one field, one word from the field, part of one word")
    public void searchBooksByOneFieldTest(String request) {
        List<String> response = bookStorePage.search(request);
        for (String responseString : response) {
            Assert.assertTrue(responseString.contains(request));
        }
    }

    //FIXME Search does not work on two fields:
    // - To pass the test, it is set to check for an empty response
    @Test(dataProvider = "bookTwoFieldsProvider", dataProviderClass = SearchDataProvider.class, groups = {"search"}, priority = 2)
    @Description("Search in two fields. Does not work for this search, an empty response is being checked")
    public void searchBooksByTwoFields(String field1, String field2) {
        List<String> response = bookStorePage.search(field1 + " " + field2);
        Assert.assertTrue(response.isEmpty());
    }

    @Test(dataProvider = "bookCaseProvider", dataProviderClass = SearchDataProvider.class, groups = {"search", "case"}, priority = 1)
    @Description("Upper case search test")
    public void upperCaseSearchTest(String request) {
        List<String> response = bookStorePage.search(request.toUpperCase());
        for (String responseString : response) {
            Assert.assertTrue(responseString.contains(request));
        }
    }

    @Test(dataProvider = "bookCaseProvider", dataProviderClass = SearchDataProvider.class, groups = {"search", "case"}, priority = 1)
    @Description("Lower case search test")
    public void lowerCaseSearchTest(String request) {
        List<String> response = bookStorePage.search(request.toLowerCase());
        for (String responseString : response) {
            Assert.assertTrue(responseString.contains(request));
        }
    }


    //FIXME Search does not trim:
    // - To pass the test, it is set to check for an empty response
    @Test(dataProvider = "trimProvider", dataProviderClass = SearchDataProvider.class, groups = {"search", "trim"}, priority = 2)
    @Description("Checking the search for trim. Does not work for this search, an empty response is being checked")
    public void trimSearchTest(String request) {
        List<String> response = bookStorePage.search(request);
        Assert.assertTrue(response.isEmpty());
    }

    //FIXME Search does not match word combinations:
    // - To pass the test, it is set to check for an empty response
    @Test(dataProvider = "wordOrderProvider", dataProviderClass = SearchDataProvider.class, groups = {"search", "order"}, priority = 2)
    @Description("Checking the search for word order. Does not work for this search, an empty response is being checked")
    public void wordOrderTest(String request) {
        List<String> response = bookStorePage.search(request);
        Assert.assertTrue(response.isEmpty());
    }

    //FIXME Search is not resistant to typos
    // - To pass the test, it is set to check for an empty response
    @Test(dataProvider = "typosProvider", dataProviderClass = SearchDataProvider.class, groups = {"search", "typos"}, priority = 2)
    @Description("Checking the search for typos. Does not work for this search, an empty response is being checked")
    public void typosTest(String request) {
        List<String> response = bookStorePage.search(request);
        Assert.assertTrue(response.isEmpty());
    }

    //FIXME Search does not accept abbreviations
    // - To pass the test, it is set to check for an empty response
    @Test(dataProvider = "abbreviationsProvider", dataProviderClass = SearchDataProvider.class, groups = {"search"}, priority = 2)
    @Description("Checking the search for abbreviations. Does not work for this search, an empty response is being checked")
    public void abbreviationsTest(String abb, String fullWord) {
        List<String> response = bookStorePage.search(abb);
        response.removeIf(responseString -> responseString.contains(abb));
        Assert.assertTrue(response.isEmpty());
    }


}