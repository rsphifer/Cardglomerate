package player;

import java.io.Serializable;

import cardgames.CardGameType;

public class AchievementRequest implements Serializable {
	private CardGameType gameType;
	private Player player;
	
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
