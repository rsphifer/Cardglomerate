package main;

import cardgames.CardGame;


/**
 * @author 	Richard Phifer <rsphifer@purdue.edu>
 * @date	Oct. 29, 2014
 * 
 * Defines an implicit data structure that holds a list of all active games in the system.
 * A game's id is its index in the array.
 */
public class GameTable {
	private final int MAX_GAMES = 50;
	
	private GameListEntry[] games;
	private int numberOfGames;
	
	public GameTable() {
		/* Initialize all table entries */
		games = new GameListEntry[MAX_GAMES];
		for (int i=0; i<MAX_GAMES; i++) {
			games[i] = new GameListEntry();
		}
	}
	
	/**
	 * Finds a free game table entry and allocates it to the game. 
	 * Synchronized in case of multiple clients creating games at the same time.
	 * Returns the game id of the newly created game or -1 if the table is full.
	 */
	public synchronized int addNewGame(CardGame game) {
		int gameId=0;
		
		if (++numberOfGames == MAX_GAMES) {
			numberOfGames--;
			return -1;
		}
		
		/* Find first free entry in table */
		while(gameId < MAX_GAMES && games[gameId].getState() != GameListEntry.FREE_ENTRY) {
			gameId++;
		}
		
		
		games[gameId].setState(GameListEntry.USED_ENTRY);
		games[gameId].setCardGame(game);
		return gameId;
	}
	
	/**
	 * Simply frees the given game id table entry.
	 */
	public synchronized void endGame(int gameId) {
		games[gameId].setState(GameListEntry.FREE_ENTRY);
		games[gameId].setCardGame(null);
		numberOfGames--;
		return;
	}
	
	
	public synchronized void updateGame(int gameId, CardGame game) {
		games[gameId].setCardGame(game);
	}
	
	public CardGame getGame(int gameId) {
		return games[gameId].getCardGame();
	}
	
	
	/**
	 * 
	 * @author 	Richard Phifer<rsphifer@purdue.edu>
	 * @date	Oct. 29, 2014
	 *
	 * Represents one entry in the game table that the server maintains.
	 */
	private class GameListEntry {
		final static int FREE_ENTRY = 0;
		final static int USED_ENTRY = 1;
		
		private CardGame cardGame;
		private int entryState;
		
		public GameListEntry() {
			cardGame = null;
			entryState = FREE_ENTRY;
		}
		
		public void setState(int state) {
			entryState = state;
		}
		
		public int getState() {
			return entryState;
		}
		
		public void setCardGame(CardGame game) {
			cardGame = game;
		}
		
		public CardGame getCardGame() {
			return cardGame;
		}
 	}

}


