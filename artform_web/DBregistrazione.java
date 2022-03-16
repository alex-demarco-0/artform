ckage com.example.demo;
import java.security.SecureRandom;

import java.sql.*;

public class ArtFormDB {

	private Connection connetti() {
		Connection conn= null;
		String url;
		String user;
		String password;
		
		try {
			conn=DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				System.out.println(" Connessione con il database avvenuta con successo ");
			}
		}catch (SQLException e) {
			
		}
		return conn;
	}
	
	//registrazione utente
	public boolean insert (Connection conn, Utente u) {
		try {
			if(!conn.isValid(5)) {
				return false;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		String query= "INSERT INTO utente (nome, cognome,username, email, numeroTelefono, password) VALUES (?,?,?,?,?,?) ";
		try {
			PreparedStatement statement= conn.prepareStatement(query);
			statement.setString(1, utente.getNome());
			
			
		}catch () {
			
		}
	}
	  
}
