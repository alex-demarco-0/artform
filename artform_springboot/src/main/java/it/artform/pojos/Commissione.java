package it.artform.pojos;

import java.util.Date;

public class Commissione {
	private final int Id;
	private final String titolo;
	private final double prezzo;
	private final Date data;
	private final String artistaUsername, clienteUsername;
	private final String indirizzoConto;
	
	public Commissione(int Id, String titolo, double prezzo, String artistaUsername, String clienteUsername, String indirizzoConto) {
		this.Id = Id;
		this.titolo = titolo;
		this.prezzo = prezzo;
		this.data = new Date();
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
