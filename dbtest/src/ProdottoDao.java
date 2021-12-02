import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProdottoDao extends Dao {
	
	public static ArrayList<Prodotto> allProdotti(Connection con) {
		ArrayList<Prodotto> lista = new ArrayList();
		
	    Statement cmd;
		try {
			cmd = con.createStatement();
			  String query = "select * from Prodotti";
		      ResultSet res = cmd.executeQuery(query);
		      while (res.next()) {
		    	  	Prodotto p = new Prodotto();
		    	  	
			        p.setId(res.getInt("id"));
			        p.setNome(res.getString("nome"));
			        p.setPrezzo(res.getDouble("prezzo"));
			        
			        lista.add(p);
		      }
		      res.close(); // chiudere le risorse DB è obbligatorio

		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    return lista;
	}
	

}
