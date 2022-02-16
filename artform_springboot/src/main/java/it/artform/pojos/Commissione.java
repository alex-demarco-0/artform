package it.artform.pojos;

import java.util.Date;

public class Commissione {
	private int Id;
	private String titolo;
	private double prezzo;
	private Date data;
	private String artistaUsername, clienteUsername;
	private String indirizzoConto;
	
	public Commissione() {}
	
	public Commissione(int Id, String titolo, double prezzo, Date data, String artistaUsername, String clienteUsername, String indirizzoConto) {
		this.Id = Id;
		this.titolo = titolo;
		this.prezzo = prezzo;
		this.data = data;
		this.artistaUsername = artistaUsername;
		this.clienteUsername = clienteUsername;
		this.indirizzoConto = indirizzoConto;
	}
	
	public int getId() {
		return Id;
	}
	
	public String getTitolo() {
		return titolo;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public Date getData() {
		return data;
	}

	public String getArtistaUsername() {
		return artistaUsername;
	}

	public String getClienteUsername() {
		return clienteUsername;
	}

	public String getIndirizzoConto() {
		return indirizzoConto;
	}

}
