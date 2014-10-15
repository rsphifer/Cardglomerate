package main;

import java.sql.*;

/**
 * Provides interface to the server to the SQL database. All queries to db come through here.
 */
public class DatabaseAccess {


	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://mydb.ics.purdue.edu/khan62?user=khan62&password=cardglomerate";

	static final String USERNAME = "khan62";
	static final String PASSWORD = "cardglomerate";

	public static void testConnection(){
		
		Connection conn = null;
		Statement func = null;

		try{
			Class.forName(JDBC_DRIVER);
	
			System.out.println("TEST CONNECTION");

			conn = DriverManager.getConnection(DB_URL);
		
			System.out.println("Creating statement...");
      		func = conn.createStatement();
      		String sql;
      		sql = "SELECT id from users";
      		ResultSet rs = func.executeQuery(sql);

			int id = rs.getInt("id");
			System.out.println(id);

			rs.close();
			func.close();
			conn.close();


		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


}
