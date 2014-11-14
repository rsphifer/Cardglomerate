package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Provides interface to the server to the SQL database for metagame. All metagame queries to db come through here.
 */
public class AchievementAccess {


	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://mydb.ics.purdue.edu/khan62";

	static final String USERNAME = "khan62";
	static final String PASSWORD = "cardglomerate";


	public static int getID(String username){/*Assumes a valid username*/

		Connection conn = null;
		Statement getfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		getfunc = conn.createStatement();
      		
			String get_id;
      			get_id = "SELECT id FROM users WHERE username=\"" + username + "\"";


			ResultSet existing = getfunc.executeQuery(get_id);
			existing.next();

			int res = existing.getInt("id");

			existing.close();
			getfunc.close();
			conn.close();
			
			return res;
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

	public static String getName(int id){/*Assumes a valid username*/

		Connection conn = null;
		Statement getfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		getfunc = conn.createStatement();
      		
			String get_name;
      			get_name = "SELECT username FROM users WHERE id=\"" + id + "\"";


			ResultSet existing = getfunc.executeQuery(get_name);
			existing.next();

			String res = existing.getString("username");

			existing.close();
			getfunc.close();
			conn.close();
			
			return res;
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

	public static boolean getStatus(int id){/*Assumes a valid username*/

		Connection conn = null;
		Statement getfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		getfunc = conn.createStatement();
      		
			String get_stat;
      			get_stat = "SELECT is_online FROM users WHERE id=\"" + id + "\"";


			ResultSet existing = getfunc.executeQuery(get_stat);
			existing.next();

			boolean res = existing.getBoolean("is_online");

			existing.close();
			getfunc.close();
			conn.close();
			
			return res;
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

	public static int getWins(int id, String game){/*Assumes a valid username and game*/

		Connection conn = null;
		Statement getfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		getfunc = conn.createStatement();

			String column = "wins_" + game;

      		
			String get_stat;
      			get_stat = "SELECT " + column + " FROM users WHERE id=\"" + id + "\"";


			ResultSet existing = getfunc.executeQuery(get_stat);
			existing.next();

			int res = existing.getInt(column);

			existing.close();
			getfunc.close();
			conn.close();
			
			return res;
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

	public static int getGames(int id, String game){/*Assumes a valid username and game*/

		Connection conn = null;
		Statement getfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		getfunc = conn.createStatement();

			String column = "games_" + game;

      		
			String get_stat;
      			get_stat = "SELECT " + column + " FROM users WHERE id=\"" + id + "\"";


			ResultSet existing = getfunc.executeQuery(get_stat);
			existing.next();

			int res = existing.getInt(column);

			existing.close();
			getfunc.close();
			conn.close();
			
			return res;
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

	public static float getWinRat(int id, String game){/*Assumes a valid username and game*/

		int num_games = getGames(id, game);

		int num_wins = getWins(id, game);

		
		float ratio = 0;

		if(num_games > 0){
			ratio = (float)num_wins / (float)num_games;
		}

		return ratio;

	}

	public static boolean addGame(int id, String game){/*Assumes a valid username and game*/

		Connection conn = null;
		Statement addfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		addfunc = conn.createStatement();

			String column = "games_" + game;

			String add_stat;
      			add_stat = "UPDATE users SET " + column + "=" + column + "+1  WHERE id=\"" + id + "\"";

			addfunc.executeUpdate(add_stat);

			addfunc.close();
			conn.close();
			
			return true;
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

	public static boolean addWin(int id, String game){/*Assumes a valid username and game*/

		Connection conn = null;
		Statement addfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		addfunc = conn.createStatement();

			String column = "wins_" + game;

			String add_stat;
      			add_stat = "UPDATE users SET " + column + "=" + column + "+1  WHERE id=\"" + id + "\"";

			addfunc.executeUpdate(add_stat);

			addfunc.close();
			conn.close();
			
			return true;
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

	public static boolean earnedAchievement(int id, String ach){/*Assumes a valid achievement*/

		Connection conn = null;
		Statement addfunc = null;

		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		
      		addfunc = conn.createStatement();

			String add_stat;
      			add_stat = "UPDATE users SET " + ach + "=1  WHERE id=\"" + id + "\"";

			addfunc.executeUpdate(add_stat);

			addfunc.close();
			conn.close();
			
			return true;
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
