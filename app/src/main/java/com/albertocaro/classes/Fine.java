package com.albertocaro.classes;

/**
 * Created by beto_ on 29/09/2017.
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
