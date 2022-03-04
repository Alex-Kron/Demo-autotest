package com.alexkron.page;

import org.openqa.selenium.WebDriver;

import java.util.List;

public interface BookStorePage {

    void init(WebDriver driver);

    List<String> search(String query);

    Integer setRowsPerPage(String query);

    List<String> getAllBooksFromPage();

    List<String> getRowPerPageOptions();

    void toNextPage();

    void toPreviousPage();

    boolean isNextPageEnable();

    boolean isPreviousPageEnable();

    Integer getPageNumber();

    Integer getTotalPages();

    void setFirstPage();

    void setMaxPage();

    boolean isSelectRowsPerPageMultiple();

    void loginButtonClick();

    String getCurrentUserName();
}
