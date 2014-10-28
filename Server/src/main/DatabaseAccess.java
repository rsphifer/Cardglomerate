package main;

import java.sql.*;
import player.Player;

/**
 * Provides interface to the server to the SQL database. All queries to db come through here.
 */
public class DatabaseAccess {


	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://mydb.ics.purdue.edu/khan62";

	static final String USERNAME = "khan62";
	static final String PASSWORD = "cardglomerate";


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

	public static int logIn(String uname_try, String pw_try){

		Connection conn = null;
		Statement checkfunc = null;
		Statement idfunc = null;
		Statement onlineflagfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		checkfunc = conn.createStatement();
      		
			String check_cred;
      			check_cred = "SELECT COUNT(id) FROM users WHERE username=\"" + uname_try + "\" AND password=\"" + pw_try + "\"";

			ResultSet verify = checkfunc.executeQuery(check_cred);
			verify.next();
			if(verify.getInt("COUNT(id)") == 1){ /*Username/password correct and unique*/

				idfunc = conn.createStatement();
				onlineflagfunc = conn.createStatement();

				String select_id;
      				select_id = "SELECT id FROM users WHERE username=\"" + uname_try + "\" AND password=\"" + pw_try + "\"";

				String online_id;
				online_id = "UPDATE users set is_online=1 WHERE username=\"" + uname_try + "\" AND password=\"" + pw_try + "\"";

				ResultSet id_res = idfunc.executeQuery(select_id);
				id_res.next();


				onlineflagfunc.executeUpdate(online_id);

				int ret = id_res.getInt("id");

				System.out.println("Logged in!");

				verify.close();
				checkfunc.close();
				idfunc.close();
				id_res.close();
				conn.close();

				return ret;
			}
			else{

				System.out.println("Username or password incorrect.");

				verify.close();
				checkfunc.close();
				idfunc.close();
				conn.close();

				return 0;
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

		return 0;
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

	/*Returns public Player data. Assumes user is logged in and ID is valid*/
	public static Player playerInfo(int id){

		Connection conn = null;
		Statement datafunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		datafunc = conn.createStatement();      		

			String user_data;
				user_data = "SELECT username, password from users WHERE id=\"" + id + "\"";

			ResultSet data = datafunc.executeQuery(user_data);
			data.next();
			
			Player newplayer = new Player(data.getString("username"), data.getString("password"));

			conn.close();
			data.close();
			datafunc.close();
			
			return newplayer;


		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		/*If execution gets here, an error occurred*/

		System.out.println("A problem occurred, please try again");
		return null;
	}

	public static boolean resetPassword(String pw, String new_pw, int id){

		Connection conn = null;
		Statement existfunc = null;
		Statement resetfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		existfunc = conn.createStatement();
			resetfunc = conn.createStatement();
      		

			String check_exists;
				check_exists = "SELECT COUNT(id) from users WHERE id=\"" + id + "\" AND password=\"" + pw + "\"";

			ResultSet existing = existfunc.executeQuery(check_exists);
			existing.next();
			if(existing.getInt("COUNT(id)") == 1){ /*id/password combination valid, create and send the new password*/

				String new_pw_sql;
      			new_pw_sql = "UPDATE users SET password=\"" + new_pw + "\" WHERE id=\"" + id + "\"";

				resetfunc.executeUpdate(new_pw_sql);

				existing.close();
				existfunc.close();
				resetfunc.close();
				conn.close();

				System.out.println("Your password has been reset.");
				return true;
			}
			else{

				System.out.println("Your current password has been entered incorrectly, please try again.");

				existing.close();
				existfunc.close();
				resetfunc.close();
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
}
