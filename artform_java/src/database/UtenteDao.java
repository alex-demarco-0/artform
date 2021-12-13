package database;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import artform.Utente;

public class UtenteDao extends Dao {
	
	public static ArrayList<Utente> allUtenti(Connection con) {
		ArrayList<Utente> lista = new ArrayList<>();
		Statement cmd;
		try {
			cmd = con.createStatement();
			String query = "select * from utente";
			ResultSet res = cmd.executeQuery(query);
			while (res.next()) {
				Utente u = new Utente(res.getInt("ID"), res.getString("nome"), res.getString("cognome"), res.getString("username"),
							res.getString("email"), res.getString("numeroTelefono"), res.getString("password"), res.getInt("punteggio"));
				lista.add(u);
			}
			res.close(); // chiudere le risorse DB e' obbligatorio

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}


}
