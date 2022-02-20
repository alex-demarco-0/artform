package it.artform.pojos;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Notifica {
	private Date data;
	private int categoria;
		/* 1 = Artista per il quale è stata attivata l'opzione di notifica pubblica un contenuto;
		 * 2 = Utente esterno attribuisce un like ad un proprio contenuto;
		 * 3 = Richiesta di commissione;
		 * 4 = Guadagno di punti;
		 * 5 = Ottenimento distintivo;
		 */
	private String descrizione;
	private String collegamento;
	private String utenteUsername;
	
	public Notifica() {}
/*
	public Notifica(String data, int categoria, String descrizione, String collegamento, String utenteUsername) throws MalformedURLException {
		this.data = new Date(Date.parse(data));
		this.categoria = categoria;
		this.utenteUsername = utenteUsername;
		// if()  generazione descrizione in base alla categoria
		this.descrizione = "";
		this.collegamento = new URL(collegamento);
		if (categoria == 1) {
			//this.descrizione = "<utenteUsername> ha pubblicato un nuovo contenuto.";
		}
		// if()  aggiunta collegamento in base a categoria ecc.
	}
*/
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public int getCategoria() {
		return categoria;
	}
	
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getCollegamento() {
		return collegamento;
	}
	
	public void setCollegamento(String collegamento) {
		this.collegamento = collegamento;
	}
	
	public String getUtenteUsername() {
		return utenteUsername;
	}
	
	public void setUtenteUsername(String utenteUsername) {
		this.utenteUsername = utenteUsername;
	}
	
}
