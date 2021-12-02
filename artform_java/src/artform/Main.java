package artform;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		Connection c = UtenteDao.openConnection();
		ArrayList<Utente> listaU = UtenteDao.allUtenti(c);

		for(Utente u: listaU)
			System.out.println(u);
		
		String query = "SELECT nome AS Nome_Utente FROM utente;";
		ResultSet nomi = UtenteDao.utenteQuery(c, query);
		try {
			while(nomi.next()) {
				System.out.println(nomi.getString("Nome_Utente"));
			}
			nomi.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		UtenteDao.closeConnection(c);

	}

}
