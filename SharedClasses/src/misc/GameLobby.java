package misc;

import java.io.Serializable;
import java.util.ArrayList;

import player.Player;

public class GameLobby implements Serializable {

	private int minPlayers, maxPlayers, lobbyId;
	private ArrayList<Player> players;
	
	public boolean isStarted;
	private int gameId;
	
	private Player lobbyHost;
	
	public GameLobby(int minPlayers, int maxPlayers, int lobbyId) {
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.lobbyId = lobbyId;
		
		players = new ArrayList<Player>();
	}
	
	public boolean addPlayer(Player p) {
		
		/* Table is full */
		if (players.size() >= maxPlayers) {
			return false;
		}
		
		/* Add player to table */
		players.add(p);
		
		/* Table is empty, set player to host */
		if (players.size() == 1) {
			setHost(p);
		}
		
		return true;
		
	}
	
	public void removePlayer(Player p) {
		/* Host is leaving, assign new host */
		if (lobbyHost != null && lobbyHost.userName.equals(p.userName)) {
			if (players.size() == 1) {
				/* No other player to give host to. */
				lobbyHost = null;
			} else {
				/* At least 1 other player to give host to */
				for (Player tmp : players) {
					if (!tmp.userName.equals(p.userName)) {
						setHost(tmp);
						break;
					}
				}
			}
		}
		
		for (int i=players.size()-1; i>=0; i--) {
			if (players.get(i).userName.equals(p.userName)) {
				players.remove(i);
			}
		}
		if (players.isEmpty()) {
			isStarted = false;
			gameId = -1;
		}
		return;
		
	}
	
	public void setHost(Player newHost) {
		boolean isValidPlayer = false;
		for (Player p : players) {
			if (newHost.userName.equals(p.userName)) {
				isValidPlayer = true;
			}
		}
		if (isValidPlayer) {
			lobbyHost = newHost;
		}
		return;
	}
	
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public int getGameId() {
		return gameId;
	}
	
	public int getLobbyId() {
		return lobbyId;
	}
	public Player getHost() {
		return lobbyHost;
	}
	
	public int getMinPlayers() {
		return minPlayers;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	
}
