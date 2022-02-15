package it.artform.pojos;

import java.util.Date;

public class Post {
	private final int Id;
	private final String utenteUsername;
	private String titolo;
	private String topic;
	private String[] tags; // oppure ArrayList<String> ?
	private final Date dataPubblicazione;
	private int like;
	private final boolean tipologia; // boolean ?
	
	
	public Post(int Id, String utenteUsername, String titolo, String topic, String[] tags, boolean tipologia) {
		this.Id = Id;
		this.utenteUsername = utenteUsername;
		this.titolo = titolo;
		this.topic = topic;
		this.tags = tags;
		this.dataPubblicazione = new Date();
		this.like = 0;
		this.tipologia = tipologia;
	}
	
	public int getId() {
		return Id;
	}
	
	public String getUtenteUsername() {
		return utenteUsername;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
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
	
	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}
	
	public int getLike() {
		return like;
	}
	
	public void addLike() {
		this.like++;
	}

	public boolean getTipologia() {
		return tipologia;
	}

}
