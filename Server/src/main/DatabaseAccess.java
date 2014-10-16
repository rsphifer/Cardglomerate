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
      			add_new = "INSERT INTO users(username, password, email) VALUES(\"" + username + "\",\"" + pw + "\",\"" + email + "\")";

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

			addfunc.executeUpdate(add_new);
			System.out.println("Account successfully created.");

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

		return true;
	}

	public static boolean logIn(String uname_try, String pw_try){

		Connection conn = null;
		Statement checkfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		checkfunc = conn.createStatement();
      		
			String check_cred;
      			check_cred = "SELECT COUNT(id) FROM users WHERE username=\"" + uname_try + "\" AND password=\"" + pw_try + "\"";

			ResultSet verify = checkfunc.executeQuery(check_cred);
			verify.next();
			if(verify.getInt("COUNT(id)") == 1){ /*Username/password correct and unique*/

				System.out.println("Logged in!");

				verify.close();
				checkfunc.close();
				conn.close();

				return true;
			}
			else{

				System.out.println("Username or password incorrect.");

				verify.close();
				checkfunc.close();
				conn.close();

				return false;
			}
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		/*If execution gets here, an error occurred*/

		System.out.println("A problem occurred, please try again");

		return false;
	}

	public static String retrievePassword(String username, String email){

		Connection conn = null;
		Statement existfunc = null;
		Statement resetfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		existfunc = conn.createStatement();
			resetfunc = conn.createStatement();
      		

			String check_exists;
				check_exists = "SELECT COUNT(id) from users WHERE email=\"" + email + "\" AND username=\"" + username + "\"";

			ResultSet existing = existfunc.executeQuery(check_exists);
			existing.next();
			if(existing.getInt("COUNT(id)") == 1){ /*Username/email combination valid, create and send the new password*/

				String randpass = Long.toHexString(Double.doubleToLongBits(Math.random()));

				String new_pw;
      			new_pw = "UPDATE users SET password=\"" + randpass + "\" WHERE username=\"" + username + "\"";

				resetfunc.executeUpdate(new_pw);

				existing.close();
				existfunc.close();
				resetfunc.close();
				conn.close();

				System.out.println("A new password will be sent to your email address.");
				return randpass;
			}
			else{

				System.out.println("There is no account with those credentials, please try again.");

				existing.close();
				existfunc.close();
				resetfunc.close();
				conn.close();

				return "";
			}

		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		/*If execution gets here, an error occurred*/

		System.out.println("A problem occurred, please try again");
		return "";
	}
}
