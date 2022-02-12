package it.artform.pojos;

public class Badge {
	private final String nome, descrizione;
	private final int punteggio;
	
	public Badge(String nome, String descrizione, int punteggio) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.punteggio = punteggio;
	}

	public String getNome() {
		return this.nome;
	}
	
	public String getDescrizione() {
		return this.descrizione;
	}
	
	public int getPunteggio() {
		return punteggio;
	}

}
