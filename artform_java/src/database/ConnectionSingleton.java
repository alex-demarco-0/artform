

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton per tenere un'istanza di connessione al DB
 * @author AlessandroDeMarco
 *
 */
public class ConnectionSingleton {
	
	private static ConnectionSingleton _instance;
	
	public static ConnectionSingleton getInstance() {
		if (_instance == null)
			_instance = new ConnectionSingleton();
		return _instance;
	}
	
	private Connection connection;
	
	private ConnectionSingleton() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Teatro", "root", "password");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Conesso al DB del Teatro.\n");
	}
	
	public Connection getConnection() {
		return connection;
	}

}
