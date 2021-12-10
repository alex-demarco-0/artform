package artform;

import java.net.URL;
import java.util.Date;

public class Notifica {
	
	private final Date data;
	private final int categoria;
		/* 1 = Artista per il quale è stata attivata l’opzione di notifica pubblica un contenuto;
		 * 2 = Utente esterno attribuisce un like ad un proprio contenuto;
		 * 3 = Richiesta di commissione;
		 * 4 = Guadagno di punti;
		 * 5 = Ottenimento distintivo;
		 */
	private String descrizione;
	private URL collegamento;
	
	public Notifica(int categoria) {
		this.data = new Date();
		this.categoria = categoria;
		// if()  generazione descrizione in base alla categoria
		if (categoria == 1) {
			this.descrizione = "<utenteID_getUsername()> ha pubblicato un nuovo contenuto.";
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
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public URL getCollegamento() {
		return collegamento;
	}
	
	public void setCollegamento(URL collegamento) {
		this.collegamento = collegamento;
	}

}
