package com.alexkron.page;

import org.openqa.selenium.WebDriver;

public interface ProfilePage {

    void init(WebDriver driver);

    String getNotLoggedMessage();

    String getUserName();
}
