package it.artform.pojos;

public class Badge {
	
	//private final int ID;
	private String nome, descrizione;
	private int punteggio;
	
	public Badge(/*int ID*/String nome, String descrizione, int punteggio) {
		//this.ID = ID;
		this.nome = nome;
		this.descrizione = descrizione;
		this.punteggio = punteggio;
	}
	/*	
	public int getID() {
		return ID;
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
