package it.artform.pojos;

public class Utente {
	private String nome, cognome, username, email, numeroTelefono, password, bio;
	private int punteggio;
	private String immagineProfiloSrc;
	
	public Utente() {}
/*
	public Utente(String nome, String cognome, String username, String email, String numeroTelefono, String password, String bio, int punteggio, String immagineProfiloSrc) {
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.numeroTelefono = numeroTelefono;
		this.password = password;
		this.bio = bio;
		this.punteggio = punteggio;
		this.immagineProfiloSrc = immagineProfiloSrc;
	}
*/
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
	
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public int getPunteggio() {
		return punteggio;
	}
	
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	
	public void incrementaPunteggio(int punteggio) {
		this.punteggio += punteggio;
	}
	
	public String getImmagineProfiloSrc() {
		return immagineProfiloSrc;
	}
	
	public void setImmagineProfiloSrc(String immagineProfiloSrc) {
		this.immagineProfiloSrc = immagineProfiloSrc;
	}
	
	/*
	@Override
	public String toString() {
		return "Utente [nome=" + nome + ", cognome=" + cognome + ", username=" + username + ", email="
				+ email + ", numeroTelefono=" + numeroTelefono + ", password=" + password + "]";
	}
*/
}