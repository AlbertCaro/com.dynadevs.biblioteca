package com.dynadevs.classes;

/**
 * Created by Alberto Caro Navarro on 29/09/2017.
 * albertcaronava@gmail.com
 */

public class Fine {
    private String Title, Sanction;

    public Fine (String Title, String Sanction) {
        this.Title = Title;
        this.Sanction = Sanction;
    }

    public String getTitle() {
        return Title;
    }

    public String getSanction() {
        return Sanction;
    }
}
