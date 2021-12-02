
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteDao extends Dao {

	public static ArrayList<Cliente> allClienti(Connection con, String initName) {
		ArrayList<Cliente> lista = new ArrayList();

		Statement cmd;
		try {
			cmd = con.createStatement();
			String query = "select * from Clienti where nome like '" + initName+"%'";
			ResultSet res = cmd.executeQuery(query);
			while (res.next()) {
				Cliente c = new Cliente();

				c.setId(res.getInt("id"));
				c.setNome(res.getString("nome"));
				c.setCognome(res.getString("cognome"));

				lista.add(c);
			}
			res.close(); // chiudere le risorse DB è obbligatorio

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}


}
