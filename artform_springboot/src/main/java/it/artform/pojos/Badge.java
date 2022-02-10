package it.artform.pojos;

public class Badge {
	
	//private final int ID;
	private String contenuto;
	private int punteggio;
	
	public Badge(/*int ID*/String contenuto, int punteggio) {
		//this.ID = ID;
		this.contenuto = contenuto;
		this.punteggio = punteggio;
	}
	/*	
	public int getID() {
		return ID;
	}
	*/
	public String getContenuto() {
		return contenuto;
	}
	
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	
	public int getPunteggio() {
		return punteggio;
	}
	
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

}
