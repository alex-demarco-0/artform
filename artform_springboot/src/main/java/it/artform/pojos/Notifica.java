package it.artform.pojos;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Notifica {
	private final Date data;
	private final int categoria;
		/* 1 = Artista per il quale è stata attivata l'opzione di notifica pubblica un contenuto;
		 * 2 = Utente esterno attribuisce un like ad un proprio contenuto;
		 * 3 = Richiesta di commissione;
		 * 4 = Guadagno di punti;
		 * 5 = Ottenimento distintivo;
		 */
	private final String descrizione;
	private final URL collegamento;
	private final String utenteUsername;
	
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
	
	public Date getData() {
		return data;
	}
	
	public int getCategoria() {
		return categoria;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public URL getCollegamento() {
		return collegamento;
	}
	
	public String getUtenteUsername() {
		return utenteUsername;
	}
	
}
