package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Dao {
	
	public static Connection openConnection() {
		String dbName = "ArtForm";
		String ipServer = "localhost:3306";
		Connection con = null;
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			String url = "jdbc:mysql://"+ipServer+"/"+dbName+"?useSSL=false";
			con = DriverManager.getConnection(url, "root", "password");
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return con;
	}

	public static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// TEST
	public static ResultSet query(Connection con, String query) {
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

}
