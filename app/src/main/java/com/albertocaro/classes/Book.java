package com.albertocaro.classes;

import java.io.Serializable;

/**
 * Created by beto_ on 29/09/2017.
 */

public class Book implements Serializable {
    private Integer id;
    private String Title, Autor, Editorial, Edition, Description;
    private int numCopies, pages;

    public Book (String Title, String Autor, String Editorial, String Edition, String Description, int numCopies, int pages) {
        this.Title = Title;
        this.Autor = Autor;
        this.Editorial = Editorial;
        this.Edition = Edition;
        this.Description = Description;
        this.numCopies = numCopies;
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

    public int getNumCopies() {
        return numCopies;
    }

    public int getPages() {
        return pages;
    }
}
