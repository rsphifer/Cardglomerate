package misc;

import java.io.Serializable;

import player.Player;
import cardgames.CardGameType;

public class GameLobbyRequest implements Serializable {

	private Player player;
	private int lobbyId;
	private CardGameType gameType;
	
	public GameLobbyRequest(Player player, int lobbyId, CardGameType gameType) {
		this.player = player;
		this.lobbyId = lobbyId;
		this.gameType = gameType;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getLobbyId() {
		return lobbyId;
	}
	
	public CardGameType getCardGameType() {
		return gameType;
	}
	
}
