package com.dynadevs.classes;

/**
 * Created by Alberto Caro Navarro on 11/11/2017.
 * albertcaronava@gmail.com
 */

public class Person {
    private String Name, Career, Email;
    private int photo;

    public Person(String name, String career, String email, int photo) {
        Name = name;
        Career = career;
        Email = email;
        this.photo = photo;
    }

    public String getName() {
        return Name;
    }

    public String getCareer() {
        return Career;
    }

    public String getEmail() {
        return Email;
    }

    public int getPhoto() {
        return photo;
    }
}
