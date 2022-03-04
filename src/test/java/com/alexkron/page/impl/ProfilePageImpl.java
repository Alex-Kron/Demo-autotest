package com.alexkron.page.impl;

import com.alexkron.page.ProfilePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePageImpl implements ProfilePage {

    @FindBy(css = "label.form-label#notLoggin-label")
    @CacheLookup
    private WebElement notLogginLabel;

    @FindBy(css = "label#userName-value")
    private WebElement userName;

    private WebDriverWait webDriverWait;

    @Override
    public void init(WebDriver driver) {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(1));
        PageFactory.initElements(driver, this);
    }

    @Override
    public String getNotLoggedMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("label.form-label#notLoggin-label")));
        return notLogginLabel.getText();
    }

    @Override
    public String getUserName() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("label#userName-value")));
        return userName.getText();
    }
}
