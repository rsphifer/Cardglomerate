package misc;

import java.io.Serializable;

import cardgames.CardGame;

public class UpdateGameRequest implements Serializable{
	
	private int gameId;
	private CardGame game;
	
	public UpdateGameRequest(int gameId, CardGame game) {
		this.gameId = gameId;
		this.game = game;
	}
	
	public int getGameId() {
		return gameId;
	}
	
	public CardGame getCardGame() {
		return game;
	}

}
