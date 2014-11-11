package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import player.Player;
import cards.Card;

@SuppressWarnings("serial")
public class War extends CardGame implements Serializable {

	private ArrayList<Player> players;
	private LinkedList<Card> winnings;
	private LinkedList<Card> cardsToDisplay;
	private Player winner;
	private boolean gameOver = false;
	private Card p1Card = null;
	private Card p2Card = null;
	
	/* Multiplayer variables */
	private int clickCounter;
	public boolean isMultiplayer;

	/**
	 * Constructor for a multiplayer game of war.
	 */
	public War(ArrayList<Player> playerNames) {
		this.players = playerNames;
		winnings = new LinkedList<Card>();
		cardsToDisplay = new LinkedList<Card>();

		isMultiplayer = true;
		clickCounter = 0;
		
		fillDeck();
		setup();

	}

	/**
	 * Constructor for a game of war against an AI opponent.
	 */
	public War(Player p1) {
		players = new ArrayList<Player>();

		this.players.add(new Player("AI"));
		this.players.add(p1);

		winnings = new LinkedList<Card>();
		cardsToDisplay = new LinkedList<Card>();
		fillDeck();
		isMultiplayer = false;
	}

	public LinkedList<Card> getCardsToDisplay() {
		return cardsToDisplay;
	}

	public LinkedList<Integer> getHandSizes() {
		LinkedList<Integer> hands = new LinkedList<Integer>();
		hands.add(players.get(0).getHandSize());
		hands.add(players.get(1).getHandSize());
		return hands;
	}

	public ArrayList<Player> getWinner() {
		ArrayList<Player> win = new ArrayList<Player>();
		win.add(winner);
		return win;
	}

	public boolean getGameover() {
		return gameOver;
	}

	private void roundWin(Player winner) {
		while (!winnings.isEmpty()) {
			this.winner.addCardToDiscard((Card) winnings.remove(0));
		}
	}

	private void roundTie() {

		Card p1Down = players.get(0).playTopCard();
		winnings.push(p1Down);
		Card p1Down2 = players.get(0).playTopCard();
		winnings.push(p1Down2);
		Card p1Down3 = players.get(0).playTopCard();
		winnings.push(p1Down3);
		Card p1Up = players.get(0).playTopCard();
		winnings.push(p1Up);

		Card p2Down = players.get(1).playTopCard();
		winnings.push(p2Down);
		Card p2Down2 = players.get(1).playTopCard();
		winnings.push(p2Down2);
		Card p2Down3 = players.get(1).playTopCard();
		winnings.push(p2Down3);
		Card p2Up = players.get(1).playTopCard();
		winnings.push(p2Up);

		score(p1Up, p2Up);
	}

	private void score(Card c1, Card c2) {
		if (c1 == null || c2 == null) {
			gameOver = true;
			return;
		}
		while (!cardsToDisplay.isEmpty()) {
			cardsToDisplay.remove();
		}
		cardsToDisplay.add(c1);
		cardsToDisplay.add(c2);

		// System.out.println(c1.getPower()+" of "+c1.getSuit()+" vs "+c2.getPower()+" of "+c2.getSuit());

		if (c1.getPower() > c2.getPower()) {
			roundWin(players.get(0));
		} else if (c1.getPower() < c2.getPower()) {
			roundWin(players.get(1));
		} else {
			roundTie();
		}
	}

	private void win(Player winner) {
		this.winner = winner;
		System.out.println(winner.userName + " won!");
	}

	public void setup() {
		for (int i = 0; i < 52; i++) {
			if (i % 2 == 0) {
				players.get(0).addCardToHand(getTopOfDeck());
			} else {
				players.get(1).addCardToHand(getTopOfDeck());
			}
		}
	}

	public void testSetup() {
		for (int i = 0; i < 51; i++) {
			players.get(0).addCardToHand(getTopOfDeck());
		}
		players.get(1).addCardToHand(getTopOfDeck());
	}
	
	public synchronized void incrementClickCounter() {
		clickCounter++;
	}
	
	public int getClickCounter() {
		return clickCounter;
	}

	public void update() {
		if (!isMultiplayer) {
			System.out.println(players.get(0).getHandSize() + "   "
					+ players.get(1).getHandSize());
			if ((p1Card = players.get(0).playTopCard()) == null) {
				gameOver = true;
			}
			if ((p2Card = players.get(1).playTopCard()) == null) {
				gameOver = true;
			}

			winnings.push(p1Card);
			winnings.push(p2Card);

			score(p1Card, p2Card);

			if (gameOver) {
				if (players.get(0).getHandSize() == 0) {
					win(players.get(1));
				} else {
					win(players.get(0));
				}
			}
		} else {
			
			if (clickCounter>=2) {
				/* Both players clicked, play a card */
				System.out.println("Play card");
				clickCounter = 0;
			}
			
		}
		return;
	}

	/**
	 * Automate a game of war for testing.
	 */
	public void play() {

		for (int i = 0; i < 52; i++) {
			if (i % 2 == 0) {
				players.get(0).addCardToHand(getTopOfDeck());
			} else {
				players.get(1).addCardToHand(getTopOfDeck());
			}
		}
		int j = 0;
		Card p1Card = null;
		Card p2Card = null;
		while (!gameOver) {
			j++;
			if (players.get(0).getHandSize() == 0) {
				if (players.get(0).resetHand()) {
					gameOver = true;
				}
			}
			if (players.get(1).getHandSize() == 0) {
				if (players.get(1).resetHand()) {
					gameOver = true;
				}
			}
			p1Card = players.get(0).playTopCard();
			p2Card = players.get(1).playTopCard();

			winnings.push(p1Card);
			winnings.push(p2Card);

			score(p1Card, p2Card);
		}
		System.out.println("Turns: " + j);
		if (players.get(0).getHandSize() == 0) {
			win(players.get(1));
		} else {
			win(players.get(0));
		}
	}

	@Override
	public ArrayList<Player> getPlayers() {
		return players;
	}

	@Override
	public Player getTurn() {
		return null;
	}

	@Override
	public int getPot() {
		return -1;
	}

	@Override
	public int getExpectedBet() {
		return -1;
	}

	@Override
	public boolean updateReady() {
		return false;
	}

	@Override
	public void setBet(Player p, int bet) {
	}

	@Override
	public void nextTurn(Player curP) {
	}

	@Override
	public void fold(Player p) {
	}
}
