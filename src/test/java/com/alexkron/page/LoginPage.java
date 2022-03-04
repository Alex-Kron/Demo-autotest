package com.alexkron.page;

import org.openqa.selenium.WebDriver;

public interface LoginPage {

    void init(WebDriver driver);

    void login(String login, String password, String outPage);

    void logout();

    String getLoggedInfoString();
}
