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
		//nextUtente++;
	}

	public static int getNextUtente() {
		return nextUtente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getID() {
		return ID;
	}

	@Override
	public String toString() {
		return "Utente [ID=" + ID + ", nome=" + nome + ", cognome=" + cognome + ", username=" + username + ", email="
				+ email + ", numeroTelefono=" + numeroTelefono + ", password=" + password + "]";
	}

}