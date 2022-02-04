package it.artform.pojos;

import java.net.URL;
import java.util.Date;

public class Notification {
    private final Date date;
    private final int category;
    private final String description, link;
    private final long userId;

    public Notification(Date date, int category, String description, String link, long userId) {
        this.date = new Date();
        this.category = category;
        this.description = description;
        this.link = link;
        this.userId = userId;
    }
}
