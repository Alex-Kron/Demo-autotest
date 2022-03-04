package com.alexkron.page.impl;

import com.alexkron.page.BookStorePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BookStorePageImpl implements BookStorePage {

    @FindBy(xpath = "//input[@id='searchBox']")
    @CacheLookup
    private WebElement searchField;

    @FindBy(css = "div.rt-table[role = 'grid'] div.rt-tr-group")
    private List<WebElement> bookStoreTable;

    @FindBy(css = ".pagination-bottom select[aria-label='rows per page']")
    @CacheLookup
    private WebElement selectRowPerPageElement;

    @FindBy(xpath = "//button[text()='Next']")
    private WebElement nextButton;

    @FindBy(xpath = "//button[text()='Previous']")
    private WebElement previousButton;

    @FindBy(css = "div.-pageJump > input")
    private WebElement pageNum;

    @FindBy(css = "span.-totalPages")
    private WebElement totalPages;

    @FindBy(css = "button#login.btn")
    private WebElement loginButton;

    @FindBy(css = "label#userName-value")
    private WebElement userNameLabel;

    private WebDriverWait webDriverWait;

    @Override
    public void init(WebDriver driver) {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(1));
        PageFactory.initElements(driver, this);
    }

    @Override
    public List<String> search(String query) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='searchBox']")));
        searchField.clear();
        searchField.sendKeys(query);
        searchField.sendKeys(Keys.ENTER);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.rt-table[role = 'grid']")));
        return getResultRows(bookStoreTable);
    }

    @Override
    public Integer setRowsPerPage(String query) {
        Select select = new Select(selectRowPerPageElement);
        select.selectByValue(query);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.rt-table[role = 'grid']")));
        return bookStoreTable.size();
    }

    @Override
    public List<String> getAllBooksFromPage() {
        searchField.clear();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.rt-table[role = 'grid']")));
        return getResultRows(bookStoreTable);
    }

    @Override
    public List<String> getRowPerPageOptions() {
        Select select = new Select(selectRowPerPageElement);
        List<String> options = new ArrayList<>();
        List<WebElement> optionsElement = select.getOptions();
        for (WebElement element : optionsElement) {
            options.add(element.getAttribute("value"));
        }
        return options;
    }

    @Override
    public void toNextPage() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextButton.click();
    }

    @Override
    public void toPreviousPage() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(previousButton));
        previousButton.click();
    }

    @Override
    public boolean isNextPageEnable() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Next']")));
        return nextButton.isEnabled();
    }

    @Override
    public boolean isPreviousPageEnable() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Previous']")));
        return previousButton.isEnabled();
    }

    @Override
    public Integer getPageNumber() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.-pageJump > input")));
        return Integer.parseInt(pageNum.getAttribute("value"));
    }

    @Override
    public Integer getTotalPages() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.rt-table[role = 'grid']")));
        return Integer.parseInt(totalPages.getText());
    }

    @Override
    public void setFirstPage() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.-pageJump > input")));
        pageNum.clear();
        pageNum.sendKeys("1");
        pageNum.sendKeys(Keys.ENTER);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.-totalPages")));
    }

    @Override
    public void setMaxPage() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.-pageJump > input")));
        pageNum.clear();
        pageNum.sendKeys(totalPages.getText());
        pageNum.sendKeys(Keys.ENTER);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.rt-table[role = 'grid']")));
    }

    @Override
    public boolean isSelectRowsPerPageMultiple() {
        Select select = new Select(selectRowPerPageElement);
        return select.isMultiple();
    }

    @Override
    public void loginButtonClick() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    @Override
    public String getCurrentUserName() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("label#userName-value")));
        return userNameLabel.getText();
    }

    private List<String> getResultRows(List<WebElement> rows) {
        List<String> resultRows = new ArrayList<>();
        for (WebElement row : rows) {
            String text = row.getText();
            if (!text.isBlank()) {
                resultRows.add(text);
            }
        }
        return resultRows;
    }

}
