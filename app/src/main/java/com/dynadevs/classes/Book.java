package com.dynadevs.classes;

import android.app.Activity;

import com.dynadevs.activities.R;

import java.io.Serializable;

/**
 * Created by Alberto Caro Navarro on 29/09/2017.
 * albertcaronava@gmail.com
 */

public class Book implements Serializable {
    private String ISBN, Title, Autor, Editorial, Edition, Description, Photo, Classification;
    private int pages, copies;

    public Book(String ISBN, String title, String autor, String editorial, String edition, String description, String photo, String classification, int pages, int copies) {
        this.ISBN = ISBN;
        Title = title;
        Autor = autor;
        Editorial = editorial;
        Edition = edition;
        Description = description;
        Photo = photo;
        Classification = classification;
        this.pages = pages;
        this.copies = copies;
    }

    public String getISBN() {
        return ISBN;
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

    public String getEdition() {
        return Edition;
    }

    public String getDescription() {
        return Description;
    }

    public String getPhoto(Activity activity) {
        return activity.getString(R.string.server_url)+"biblioteca/images/"+Photo;
    }

    public String getClassification() {
        return Classification;
    }

    public int getPages() {
        return pages;
    }

    public int getCopies() {
        return copies;
    }
}
