package it.artform.pojos;

public class Badge {
	private String nome, descrizione;
	private int punteggio;
	
	public Badge() {}
/*
	public Badge(String nome, String descrizione, int punteggio) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.punteggio = punteggio;
	}
*/
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescrizione() {
		return this.descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public int getPunteggio() {
		return punteggio;
	}
	
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

}
