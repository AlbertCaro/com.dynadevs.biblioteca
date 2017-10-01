package com.albertocaro.classes;

/**
 * Created by beto_ on 30/09/2017.
 */

public class User {
    private Integer Code;
    private String Name, Career, Password;

    public User(Integer code, String name, String career, String password) {
        Code = code;
        Name = name;
        Career = career;
        Password = password;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCareer() {
        return Career;
    }

    public void setCareer(String career) {
        Career = career;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
