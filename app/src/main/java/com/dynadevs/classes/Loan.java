package com.dynadevs.classes;

/**
 * Created by beto_ on 29/09/2017.
 */

public class Loan {
    private String Title, Date;

    public Loan (String Title, String Date) {
        this.Title = Title;
        this.Date = Date;
    }

    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return Date;
    }
}
