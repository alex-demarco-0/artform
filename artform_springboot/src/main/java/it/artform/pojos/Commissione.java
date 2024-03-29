package it.artform.pojos;

import java.util.Date;

public class Commissione {
	private int Id;
	private String titolo;
	private double prezzo;
	private String descrizione;
	private String topic;
	private Date data;
	private Date dataTermine;
	private String artistaUsername, clienteUsername;
	private String indirizzoConto;
	
	public Commissione() {}
/*
	public Commissione(int Id, String titolo, double prezzo, Date data, String artistaUsername, String clienteUsername, String indirizzoConto) {
		this.Id = Id;
		this.titolo = titolo;
		this.prezzo = prezzo;
		this.data = data;
		this.artistaUsername = artistaUsername;
		this.clienteUsername = clienteUsername;
		this.indirizzoConto = indirizzoConto;
	}
*/
	public int getId() {
		return Id;
	}
	
	public void setId(int id) {
		Id = id;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public double getPrezzo() {
		return prezzo;
	}
	
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public Date getDataTermine() {
		return dataTermine;
	}
	
	public void setDataTermine(Date dataTermine) {
		this.dataTermine = dataTermine;
	}

	public String getArtistaUsername() {
		return artistaUsername;
	}
	
	public void setArtistaUsername(String artistaUsername) {
		this.artistaUsername = artistaUsername;
	}
	
	public String getClienteUsername() {
		return clienteUsername;
	}
	
	public void setClienteUsername(String clienteUsername) {
		this.clienteUsername = clienteUsername;
	}

	public String getIndirizzoConto() {
		return indirizzoConto;
	}
	
	public void setIndirizzoConto(String indirizzoConto) {
		this.indirizzoConto = indirizzoConto;
	}

}
