package misc;

import java.io.Serializable;

import player.Player;
import cardgames.CardGame;

@SuppressWarnings("serial")
public class GameCreationRequest implements Serializable{
	private Player 		gameRequester;
	private CardGame	gameRequested;
	
	public GameCreationRequest(Player gameRequester, CardGame gameRequested) {
		this.gameRequester = gameRequester;
		this.gameRequested = gameRequested;
	}
	
	public Player getGameRequester() {
		return gameRequester;
	}
	
	public CardGame getGameRequested() {
		return gameRequested;
	}
}
