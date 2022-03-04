package com.alexkron.page.impl;

import com.alexkron.page.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageImpl implements LoginPage {

    @FindBy(css = "input#userName")
    private WebElement loginField;

    @FindBy(css = "input#password")
    private WebElement passwordField;

    @FindBy(css = "button#login")
    private WebElement loginButton;

    @FindBy(css = "label.form-label#loading-label")
    private WebElement youAreLoggedLabel;

    @FindBy(xpath = "//button[text()='Log out']")
    private WebElement logOutButton;

    private WebDriverWait webDriverWait;

    @Override
    public void init(WebDriver driver) {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(1));
        PageFactory.initElements(driver, this);
    }

    @Override
    public void login(String login, String password, String outPage) {
        loginField.clear();
        passwordField.clear();
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
        webDriverWait.until(ExpectedConditions.urlMatches(outPage));
    }

    @Override
    public void logout() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Log out']")));
        logOutButton.click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(loginButton));
    }

    @Override
    public String getLoggedInfoString() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("label.form-label#loading-label")));
        return youAreLoggedLabel.getText();
    }
}
