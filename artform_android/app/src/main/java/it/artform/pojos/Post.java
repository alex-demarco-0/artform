package it.artform.pojos;

public class Post {
    private String contentSrc;
    private String user;
    private String tags;

    public Post(String contentSrc, String user, String tags) {
        this.contentSrc = contentSrc;
        this.user = user;
        this.tags = tags;
    }

    public String getContentSrc() {
        return this.contentSrc;
    }

    public String getUser() {
        return this.user;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
