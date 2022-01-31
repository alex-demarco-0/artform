package it.artform.pojos;

public class Badge {
	
	private final int ID;
	private String contenuto;
	private int punteggio;
	
	public Badge(int ID) {
		this.ID = ID;
		this.contenuto = "";
		this.punteggio = 0;
	}
		
	public int getID() {
		return ID;
	}
	
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
