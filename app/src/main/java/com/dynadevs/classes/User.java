package com.dynadevs.classes;

import java.io.Serializable;

/**
 * Created by beto_ on 30/09/2017.
 */

public class User implements Serializable {
    private String Code, Name, Email, University, Career, CareerAbrebiation, DrawerHeader;

    public User(String code, String name, String email, String university, String career, String careerAbrebiation, String drawerHeader) {
        Code = code;
        Name = name;
        Email = email;
        University = university;
        Career = career;
        CareerAbrebiation = careerAbrebiation;
        DrawerHeader = drawerHeader;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUniversity() {
        return University;
    }

    public void setUniversity(String university) {
        University = university;
    }

    public String getCareer() {
        return Career;
    }

    public void setCareer(String career) {
        Career = career;
    }

    public String getCareerAbrebiation() {
        return CareerAbrebiation;
    }

    public void setCareerAbrebiation(String careerAbrebiation) {
        CareerAbrebiation = careerAbrebiation;
    }

    public String getDrawerHeader() {
        return DrawerHeader;
    }

    public void setDrawerHeader(String drawerHeader) {
        DrawerHeader = drawerHeader;
    }
}
