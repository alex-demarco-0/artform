package artform;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UtenteDao extends Dao {

	public static ResultSet utenteQuery(Connection con, String query) {
		Statement cmd;
		try {
			cmd = con.createStatement();
			if (query.charAt(0) == 's' || query.charAt(0) == 'S') {
				ResultSet res = cmd.executeQuery(query);
				return res;
			}
			else {
				cmd.executeUpdate(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static ArrayList<Utente> allUtenti(Connection con) {
		ArrayList<Utente> lista = new ArrayList<>();
		Statement cmd;
		try {
			cmd = con.createStatement();
			String query = "select * from utente";
			ResultSet res = cmd.executeQuery(query);
			while (res.next()) {
				Utente u = new Utente(res.getInt("ID"), res.getString("nome"), res.getString("cognome"), res.getString("username"),
							res.getString("email"), res.getString("numeroTelefono"), res.getString("password"));
				lista.add(u);
			}
			res.close(); // chiudere le risorse DB e' obbligatorio

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}


}
