package it.artform.pojos;

import java.util.Date;

public class Post {
    private String contentSrc, user, title, topic, tags;
    private final Date publicationDate ;
    private int like;
    private final boolean type; //true = image, false = video

    public Post(String contentSrc, String user, String title, String topic, String tags, Date publicationDate, int like, boolean type) {
        this.contentSrc = contentSrc;
        this.user = user;
        this.title = title;
        this.topic = topic;
        this.tags = tags;
        this.publicationDate = publicationDate;
        this.like = like;
        this.type = type;
    }

    public String getContentSrc() {
        return this.contentSrc;
    }

    public String getUser() {
        return this.user;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTopic() {
        return this.topic;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getPublicationDate() {
        return this.publicationDate;
    }

    public int getLike() {
        return this.like;
    }

    public void giveLike() {
        this.like++;
    }

    public boolean getType() {
        return this.type;
    }
}
