package it.artform.pojos;

import java.net.URL;
import java.util.Date;

public class Notification {
    private final Date date;
    private final int category;
    private String description;
    private String link;
    private long userID;

    public Notification(Date date, int category, String description, String link, long userID) {
        this.date = new Date();
        this.category = category;
        this.description = description;
        this.link = link;
        this.userID = userID;
    }
}
