package main;

import java.sql.*;

/**
 * Provides interface to the server to the SQL database. All queries to db come through here.
 */
public class DatabaseAccess {


	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://mydb.ics.purdue.edu/khan62";

	static final String USERNAME = "khan62";
	static final String PASSWORD = "cardglomerate";

	public static void testConnection(){
		
		Connection conn = null;
		Statement func = null;

		try{
			Class.forName(JDBC_DRIVER);
	
			System.out.println("TEST CONNECTION");

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
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

	public static boolean createAccount(String username, String pw, String email){

		Connection conn = null;
		Statement existfunc = null;
		Statement addfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		existfunc = conn.createStatement();
			addfunc = conn.createStatement();
      		
			String add_new;
      			add_new = "INSERT INTO users(username, password, email) VALUES(" + username + "," + pw + "," + email + ")";

			String check_exists;
				check_exists = "SELECT COUNT(id) from users WHERE email=\"" + email + "\" OR username=\"" + username + "\"";


			ResultSet existing = existfunc.executeQuery(check_exists);
			existing.next();
			if(existing.getInt("COUNT(id)") != 0){ /*Username or email already used*/

				System.out.println("Username or email address already used.");

				existing.close();
				existfunc.close();
				addfunc.close();
				conn.close();

				return false;
			}

			addfunc.executeQuery(add_new);

			existing.close();
			existfunc.close();
			addfunc.close();
			conn.close();

		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Account successfully created.");

		return true;
	}

}
