package com.dynadevs.classes;

import android.app.Activity;

import com.dynadevs.activities.R;

import java.io.Serializable;

/**
 * Created by Alberto Caro Navarro on 29/09/2017.
 * albertcaronava@gmail.com
 */

public class Book implements Serializable {
    private String ISBN, Title, Autor, Edition, Photo, Estatus, Classification;

    public Book(String ISBN, String title, String autor, String edition, String photo, String estatus, String classification) {
        this.ISBN = ISBN;
        Title = title;
        Autor = autor;
        Edition = edition;
        Photo = photo;
        Estatus = estatus;
        Classification = classification;
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

    public String getEdition() {
        return Edition;
    }

    public String getPhoto(Activity activity) {
        return activity.getString(R.string.server_url)+"biblioteca/images/"+Photo;
    }

    public String getEstatus() {
        return Estatus;
    }

    public String getClassification() {
        return Classification;
    }
}
