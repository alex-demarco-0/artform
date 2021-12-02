import java.sql.Connection;
import java.util.ArrayList;


public class ConnDB {

	public static void main(String[] args) {
		Connection c = ProdottoDao.openConnection();
		ArrayList<Prodotto> listaP = ProdottoDao.allProdotti(c);
		ArrayList<Cliente> listaC = ClienteDao.allClienti(c, "M");
		ProdottoDao.closeConnection(c);
		
		for(Prodotto p: listaP)
			System.out.println(p);

		System.out.println("");

		for(Cliente cl: listaC)
			System.out.println(cl);
	}

}
