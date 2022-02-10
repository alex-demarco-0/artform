package it.artform.pojos;

import java.util.Date;

public class Commissione {
	String titolo;
	double prezzo;
	Date data;
	String artista, cliente;
	String indirizzoConto;
	
	public Commissione(String titolo, double prezzo, String artista, String cliente, String indirizzoConto) {
		this.titolo = titolo;
		this.prezzo = prezzo;
		this.data = new Date();
		this.artista = artista;
		this.cliente = cliente;
		this.indirizzoConto = indirizzoConto;
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

	public String getArtista() {
		return artista;
	}

	public String getCliente() {
		return cliente;
	}

	public String getIndirizzoConto() {
		return indirizzoConto;
	}


}
