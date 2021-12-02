package artform;

public class Utente {
	
	private static int nextUtente = 1;
	private final int ID;
	private String nome, cognome, username, email, numeroTelefono, password;
	
	public Utente(String nome, String cognome, String username, String numeroTelefono, String password) {
		ID = nextUtente;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.numeroTelefono = numeroTelefono;
		this.password = password;
		nextUtente++;
	}
	
}
