package it.artform.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Notification {
    @SerializedName("data")
    private final Date date; // server DB PK
    @SerializedName("categoria")
    private final int category;
    @SerializedName("descrizione")
    private final String description;
    @SerializedName("collegamento")
    private final String link;
    @SerializedName("utenteUsername")
    private final String userUsername;

    public Notification(Date date, int category, String description, String link, String userUsername) {
        this.date = date;
        this.category = category;
        this.description = description;
        this.link = link;
        this.userUsername = userUsername;
    }

    public Date getDate() {
        return date;
    }

    public int getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getUser() {
        return userUsername;
    }

}
