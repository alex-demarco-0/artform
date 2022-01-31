package it.artform.pojos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.artform.database.Dao;
import it.artform.database.UtenteDao;

public class Main {

	public static void main(String[] args) {
		
		// test query 1 (Utente)
		Connection c = UtenteDao.openConnection();
		ArrayList<Utente> listaU = UtenteDao.allUtenti(c);
		for(Utente u: listaU)
			System.out.println(u);
		
		// test query 2 (Utente)
		String query = "SELECT nome AS Nome_Utente FROM utente;";
		ResultSet nomi = UtenteDao.query(c, query);
		try {
			while(nomi.next()) {
				System.out.println(nomi.getString("Nome_Utente"));
			}
			nomi.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		UtenteDao.closeConnection(c);

		// test query 3 ()
		Connection c2 = Dao.openConnection();
		
	}

}
