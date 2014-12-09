package misc;

import java.io.Serializable;

import player.Player;
import cardgames.CardGameType;

public class AchievementRequest implements Serializable {

	private Player player;
	private CardGameType gameType;
	
	public AchievementRequest(Player player, CardGameType gameType) {
		this.player = player;
		this.gameType = gameType;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public CardGameType getGameType() {
		return gameType;
	}
}
