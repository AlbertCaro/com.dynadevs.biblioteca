package com.dynadevs.classes;

import java.io.Serializable;

/**
 * Created by beto_ on 30/09/2017.
 */

public class User implements Serializable {
    private String Code;
    private String Name, Career;

    public User(String code, String name, String career) {
        Code = code;
        Name = name;
        Career = career;
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

    public String getCareer() {
        return Career;
    }

    public void setCareer(String career) {
        Career = career;
    }
}
