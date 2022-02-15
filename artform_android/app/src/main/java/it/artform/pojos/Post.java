package it.artform.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Post {
    @SerializedName("Id")
    private final long id; // server DB PK
    private final String userUsername;
    private String title;
    private String topic;
    private String[] tags;
    private final Date publicationDate;
    private int like;
    private final boolean type; //true = image, false = video
    private String contentSrc; // directory media file

    public Post(long id, String userUsername, String title, String topic, String[] tags, Date publicationDate, int like, boolean type) {
        this.id = id;
        this.userUsername = userUsername;
        this.title = title;
        this.topic = topic;
        this.tags = tags;
        this.publicationDate = publicationDate;
        this.like = like;
        this.type = type;
        this.contentSrc = null;
    }

    public long getId() { return this.id; }

    public String getUser() {
        return this.userUsername;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setTags(String[] tags) {
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

    public String getContentSrc() {
        return this.contentSrc;
    }

    public void setContentSrc(String contentSrc) {
        this.contentSrc = contentSrc;
    }

}
