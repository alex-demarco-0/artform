package artform;

public class Utente {
	
	//private static int nextUtente = 1;
	private final int ID;
	private String nome, cognome, username, email, numeroTelefono, password;
	
	public Utente(int ID, String nome, String cognome, String username, String numeroTelefono, String password) {
		this.ID = ID;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.numeroTelefono = numeroTelefono;
		this.password = password;
		//nextUtente++;
	}
	public String toString(){
		return "Ciao " + nome + " " + cognome + " detto anche " + username;
	}
}
