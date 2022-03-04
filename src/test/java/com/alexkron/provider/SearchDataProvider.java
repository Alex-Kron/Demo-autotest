package com.alexkron.provider;

import org.testng.annotations.DataProvider;

public class SearchDataProvider {

    @DataProvider(name = "bookFieldProvider")
    public static Object[][] bookFieldProvider() {
        return new Object[][] {

                //----------------------------------
                //FULL COMPLIANCE
                //----------------------------------

                //TITLES

                {"Git Pocket Guide"},
                {"Learning JavaScript Design Patterns"},
                {"Designing Evolvable Web APIs with ASP.NET"},
                {"Speaking JavaScript"},
                {"You Don't Know JS"},
                {"Programming JavaScript Applications"},
                {"Eloquent JavaScript, Second Edition"},
                {"Understanding ECMAScript 6"},

                //AUTHORS

                {"Richard E. Silverman"},
                {"Addy Osmani"},
                {"Glenn Block et al."},
                {"Axel Rauschmayer"},
                {"Kyle Simpson"},
                {"Eric Elliott"},
                {"Marijn Haverbeke"},
                {"Nicholas C. Zakas"},

                //PUBLISHERS

                {"O'Reilly Media"},
                {"No Starch Press"},

                //----------------------------------
                //ONE WORD
                //----------------------------------

                //TITLE WORDS

                {"Pocket"},
                {"JavaScript"},
                {"ASP.NET"},
                {"Speaking"},
                {"JS"},
                {"Applications"},
                {"Second"},

                //AUTHOR WORDS

                {"Silverman"},
                {"Addy"},
                {"al."},
                {"Rauschmayer"},
                {"Marijn"},

                //PUBLISHERS WORDS

                {"O'Reilly"},
                {"Media"},
                {"Press"},

                //----------------------------------
                //INCOMPLETE WORD
                //----------------------------------

                //INCOMPLETE TITLE WORDS

                {"Gi"},
                {"Script"},
                {"able"},
                {"API"},
                {"Java"},
                {"ECMA"},

                //INCOMPLETE AUTHOR WORDS

                {"Silver"},
                {"Osman"},
                {"Axe"},
                {"mayer"},

                //INCOMPLETE PUBLISHER WORDS

                {"Reilly"},
                {"ress"},
        };
    }

    @DataProvider(name = "bookTwoFieldsProvider")
    public static Object[][] bookTwoFieldsProvider() {
        return new Object[][] {

                //----------------------------------
                //FULL FIELDS
                //----------------------------------
                {"Git Pocket Guide", "O'Reilly Media"},

                //----------------------------------
                //TWO WORDS IN FIELDS
                //----------------------------------
                {"ECMAScript 6", "Starch Press"},

                //----------------------------------
                //INCOMPLETE WORDS IN FIELDS
                //----------------------------------
                {"Speak", "Rausch"}
        };
    }

    @DataProvider(name = "bookCaseProvider")
    public static Object[][] bookCaseProvider() {
        return new Object[][] {
            {"Git Pocket Guide"},
            {"Addy Osmani"},
            {"No Starch Press"}
        };
    }

    @DataProvider(name = "trimProvider")
    public static Object[][] trimProvider() {
        return new Object[][] {
                {"Git Pocket Guide               "},
                {"        Addy Osmani        "},
                {"  No Starch Press  "}
        };
    }

    @DataProvider(name = "wordOrderProvider")
    public static Object[][] wordOrderProvider() {
        return new Object[][] {
                {"Guide Git Pocket"},
                {"Osmany Addy"},
                {"Press No Starch"}
        };
    }

    @DataProvider(name = "typosProvider")
    public static Object[][] typosProvider() {
        return new Object[][] {
                {"Git Pocet Guide"},
                {"Ady Osmani"},
                {"Noo Starch Press"}
        };
    }

    @DataProvider(name = "abbreviationsProvider")
    public static Object[][] abbreviationsProvider() {
        return new Object[][] {
                {"JS", "JavaScript"}
        };
    }
}
