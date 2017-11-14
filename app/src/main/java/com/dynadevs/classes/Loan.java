package com.dynadevs.classes;

/**
 * Created by Alberto Caro Navarro on 29/09/2017.
 * albertcaronava@gmail.com
 */

public class Loan {
    private String Title, ISBN;
    int Day, Mounth, Year;

    public Loan(String title, String ISBN, int day, int mounth, int year) {
        Title = title;
        this.ISBN = ISBN;
        Day = day;
        Mounth = mounth;
        Year = year;
    }

    public String getTitle() {
        return Title;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getDay() {
        return Day;
    }

    public int getMounth() {
        return Mounth;
    }

    public int getYear() {
        return Year;
    }
}
