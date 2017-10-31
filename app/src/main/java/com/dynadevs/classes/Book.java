package com.dynadevs.classes;

import java.io.Serializable;

/**
 * Created by beto_ on 29/09/2017.
 */

public class Book implements Serializable {
    private Integer id;
    private String Title, Autor, Editorial, Edition, Description, Photo;
    private int pages;

    public Book (String Title, String Autor, String Editorial, String Edition, String Description, String Photo, int pages) {
        this.Title = Title;
        this.Autor = Autor;
        this.Editorial = Editorial;
        this.Edition = Edition;
        this.Photo = Photo;
        this.Description = Description;
        this.pages = pages;
    }

    public String getTitle() {
        return Title;
    }

    public String getAutor() {
        return Autor;
    }

    public String getEditorial() {
        return Editorial;
    }

    public String getDescription() {
        return Description;
    }

    public String getEdition() {
        return Edition;
    }

    public String getPhoto() {
        return "http://albertocaro.000webhostapp.com/biblioteca/images/"+Photo;
    }

    public int getPages() {
        return pages;
    }
}
