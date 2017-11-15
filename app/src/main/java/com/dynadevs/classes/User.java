package com.dynadevs.classes;

import java.io.Serializable;

/**
 * Created by Alberto Caro Navarro on 30/09/2017.
 * albertcaronava@gmail.com
 */

public class User implements Serializable {
    private String Code, Name, Email, University, Career, Acronym, DrawerHeader;

    public User(String code, String name, String email, String university, String career, String acronym, String drawerHeader) {
        Code = code;
        Name = name;
        Email = email;
        University = university;
        Career = career;
        Acronym = acronym;
        DrawerHeader = drawerHeader;
    }

    public String getCode() {
        return Code;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getUniversity() {
        return University;
    }

    public String getCareer() {
        return Career;
    }

    public String getAcronym() {
        return Acronym;
    }

    public String getDrawerHeader() {
        return DrawerHeader;
    }
}
