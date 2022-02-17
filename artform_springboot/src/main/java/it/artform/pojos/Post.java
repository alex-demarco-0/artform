package it.artform.pojos;

import java.util.Arrays;
import java.util.Date;

public class Post {
	private int Id;
	private String utenteUsername, titolo, topic;
	private String[] tags; // oppure ArrayList<String> ?
	private Date dataPubblicazione;
	private int like;
	private boolean tipologia; // boolean ?
	private String contenutoSrc;
	
	public Post() {}
/*
	public Post(int Id, String utenteUsername, String titolo, String topic, String[] tags, Date dataPubblicazione, boolean tipologia, String contenutoSrc) {
		this.Id = Id;
		this.utenteUsername = utenteUsername;
		this.titolo = titolo;
		this.topic = topic;
		//String iterator
		this.tags = tags;
		this.dataPubblicazione = dataPubblicazione;
		this.like = 0;
		this.tipologia = tipologia;
		this.contenutoSrc = contenutoSrc;
	}
*/
	public int getId() {
		return Id;
	}
	
	public void setId(int id) {
		Id = id;
	}
	
	public String getUtenteUsername() {
		return utenteUsername;
	}
	
	public void setUtenteUsername(String utenteUsername) {
		this.utenteUsername = utenteUsername;
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
		//String iterator
		this.tags = tags;
	}
	
	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}
	
	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
	
	public int getLike() {
		return like;
	}
	
	public void setLike(int like) {
		this.like = like;
	}
	
	public void addLike() {
		this.like++;
	}

	public boolean getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(boolean tipologia) {
		this.tipologia = tipologia;
	}
	
	public String getContenutoSrc() {
		return contenutoSrc;
	}
	
	public void setContenutoSrc(String contenutoSrc) {
		this.contenutoSrc = contenutoSrc;
	}
/*
	@Override
	public String toString() {
		return "Post [Id=" + Id + ", utenteUsername=" + utenteUsername + ", titolo=" + titolo + ", topic=" + topic
				+ ", tags=" + Arrays.toString(tags) + ", dataPubblicazione=" + dataPubblicazione + ", like=" + like
				+ ", tipologia=" + tipologia + ", contenutoSrc=" + contenutoSrc + "]";
	}
*/
}
