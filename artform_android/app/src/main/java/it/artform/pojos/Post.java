package it.artform.pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    @SerializedName("Id")
    private final int id; // server DB PK
    @SerializedName("utenteUsername")
    private final String userUsername;
    @SerializedName("titolo")
    private String title;
    private String topic;
    private String tags;
    @SerializedName("dataPubblicazione")
    private final Date publicationDate;
    private int like;
    @SerializedName("tipologia")
    private final boolean type; //true = image, false = video
    @SerializedName("contenutoSrc")
    private final String contentSrc; // directory media file

    public Post(int id, String userUsername, String title, String topic, String tags, Date publicationDate, int like, boolean type, String contentSrc) {
        this.id = id;
        this.userUsername = userUsername;
        this.title = title;
        this.topic = topic;
        this.tags = tags;
        this.publicationDate = publicationDate;
        this.like = like;
        this.type = type;
        this.contentSrc = contentSrc;
    }

    public int getId() { return this.id; }

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

    public String getContentSrc() {
        return this.contentSrc;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userUsername='" + userUsername + '\'' +
                ", title='" + title + '\'' +
                ", topic='" + topic + '\'' +
                ", tags='" + tags + '\'' +
                ", publicationDate=" + publicationDate +
                ", like=" + like +
                ", type=" + type +
                ", contentSrc='" + contentSrc + '\'' +
                '}';
    }

}
