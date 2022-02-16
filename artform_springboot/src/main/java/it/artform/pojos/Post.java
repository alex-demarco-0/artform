package it.artform.pojos;

import java.util.Date;

public class Post {
	private int Id;
	private String utenteUsername;
	private String titolo;
	private String topic;
	private String[] tags; // oppure ArrayList<String> ?
	private Date dataPubblicazione;
	private int like;
	private boolean tipologia; // boolean ?
	private String contenutoSrc;
	
	public Post() {}
	
	public Post(int Id, String utenteUsername, String titolo, String topic, String[] tags, Date dataPubblicazione, boolean tipologia, String contenutoSrc) {
		this.Id = Id;
		this.utenteUsername = utenteUsername;
		this.titolo = titolo;
		this.topic = topic;
		this.tags = tags;
		this.dataPubblicazione = dataPubblicazione;
		this.like = 0;
		this.tipologia = tipologia;
		this.contenutoSrc = contenutoSrc;
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
	
	public String getContenutoSrc() {
		return contenutoSrc;
	}

}
