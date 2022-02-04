package it.artform.pojos;

import java.util.Date;

public class Post {
	
	private final int ID;
	private final Date dataPubblicazione ;
	private String topic;
	private String[] tags; // oppure ArrayList<String>
	private int like;
	private final boolean tipologia;
	
	public Post(int ID, String topic, String[] tags, boolean tipologia) {
		this.ID = ID;
		this.dataPubblicazione = new Date();
		this.topic = topic;
		this.tags = tags;
		this.like = 0;
		this.tipologia = tipologia;
	}
	
	public int getID() {
		return ID;
	}
	
	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public String[] getTags() {
		return tags;
	}
	
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	public int getLike() {
		return like;
	}
	
	public void addLike() {
		this.like++;
	}

}
