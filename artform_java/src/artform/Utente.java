package artform;

public class Utente {
	
	private static int nextUtente = 1;
	private final int ID;
	private String nome, cognome, username, email, numeroTelefono, password;
	
	public Utente(String n, String c, String u) {
		
	}
	public String toString(){
		return "Ciao " + nome + " " + cognome + " detto anche " + username;
	}
}