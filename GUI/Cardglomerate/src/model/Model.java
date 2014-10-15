package model;

import player.Player;
import cardgames.CardGame;


/**
 * 
 * Maintains Player and CardGame states for client application.
 * 
 */
public class Model {

	private Player player;
	private CardGame currentGame;
	
	public Model(Player player) {
		this.player = player;
		
		//Other things that we cant think of
	}

	public CardGame getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(CardGame currentGame) {
		this.currentGame = currentGame;
	}

	public Player getPlayer() {
		return player;
	}
	
	
}
