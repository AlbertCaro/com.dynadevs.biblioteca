package com.dynadevs.classes;

import android.app.Activity;

import com.dynadevs.activities.R;

import java.io.Serializable;

/**
 * Created by beto_ on 29/09/2017.
 */

public class Book implements Serializable {
    private String ISBN, Title, Autor, Editorial, Edition, Description, Photo;
    private int pages, copies;

    public Book (String ISBN, String Title, String Autor, String Editorial, String Edition, String Description, String Photo, int pages, int copies) {
        this.ISBN = ISBN;
        this.Title = Title;
        this.Autor = Autor;
        this.Editorial = Editorial;
        this.Edition = Edition;
        this.Photo = Photo;
        this.Description = Description;
        this.pages = pages;
        this.copies = copies;
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

    public String getPhoto(Activity activity) {
        return activity.getString(R.string.server_url)+"biblioteca/images/"+Photo;
    }

    public int getPages() {
        return pages;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
