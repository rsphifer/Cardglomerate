package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import player.Player;
import cards.Card;

@SuppressWarnings("serial")
public class War extends CardGame implements Serializable{

	private ArrayList<Player> players;
	private LinkedList<Card> winnings;
	private LinkedList<Card> cardsToDisplay;
	private Player winner;
	private CardGame cardGame;
	private boolean gameOver = false;
	private Card p1Card = null;
	private Card p2Card = null;
	
	public War(ArrayList<Player> playerNames){
		this.players = playerNames;
		winnings = new LinkedList<Card>();
		cardsToDisplay = new LinkedList<Card>();
		this.cardGame = new CardGame();
		cardGame.fillDeck();
		cardGame.connect();
		//play();
	}
	
	public War(Player p1){
		players = new ArrayList<Player>();
		this.players.add(new Player("AI","asd"));
		this.players.add(p1);
		winnings = new LinkedList<Card>();
		cardsToDisplay = new LinkedList<Card>();
		this.cardGame = new CardGame();
		cardGame.fillDeck();
		cardGame.connect();
		//play();
	}

	public LinkedList<Card> getCardsToDisplay(){
		return cardsToDisplay;
	}
	
	public LinkedList<Integer> getHandSizes(){
		LinkedList<Integer> hands = new LinkedList<Integer>();
		hands.add(this.players.get(0).getHandSize());
		hands.add(this.players.get(1).getHandSize());
		return hands;
	}

	public Player getWinner(){
		return this.winner;
	}
	
	public boolean getGameover(){
		return this.gameOver;
	}

	private void roundWin(Player winner){
		while(!this.winnings.isEmpty()){
			winner.addCardToDiscard((Card)this.winnings.remove(0));
		}
	}

	private void roundTie(){
		//System.out.println("WAR!");
		
		Card p1Down = this.players.get(0).playTopCard();
		this.winnings.push(p1Down);
		Card p1Down2 = this.players.get(0).playTopCard();
		this.winnings.push(p1Down2);
		Card p1Down3 = this.players.get(0).playTopCard();
		this.winnings.push(p1Down3);
		Card p1Up = this.players.get(0).playTopCard();
		this.winnings.push(p1Up);
		
		Card p2Down = this.players.get(1).playTopCard();
		this.winnings.push(p2Down);
		Card p2Down2 = this.players.get(1).playTopCard();
		this.winnings.push(p2Down2);
		Card p2Down3 = this.players.get(1).playTopCard();
		this.winnings.push(p2Down3);
		Card p2Up = this.players.get(1).playTopCard();
		this.winnings.push(p2Up);

		this.score(p1Up,p2Up);
	}

	private void score(Card c1, Card c2){
		if(c1==null || c2==null) {
			this.gameOver = true;
			return;
		}
		while(!cardsToDisplay.isEmpty()){
			cardsToDisplay.remove();
		}
		cardsToDisplay.add(c1);
		cardsToDisplay.add(c2);
		
		//System.out.println(c1.getPower()+" of "+c1.getSuit()+" vs "+c2.getPower()+" of "+c2.getSuit());
		
		if(c1.getPower() > c2.getPower()){
			roundWin(this.players.get(0));
		} else if(c1.getPower() < c2.getPower()){
			roundWin(this.players.get(1));
		} else {
			roundTie();
		}
	}
	
	private void win(Player winner){
		this.winner = winner;
		System.out.println(winner.userName+" won!");
	}
	
	public void setup(){
		for(int i=0;i<52;i++){
			if(i%2 == 0){
				this.players.get(0).addCardToHand(this.cardGame.getTopOfDeck());
			} else {
				this.players.get(1).addCardToHand(this.cardGame.getTopOfDeck());
			}
		}
	}
	
	public void testSetup(){
		for(int i=0;i<51;i++){
			this.players.get(0).addCardToHand(this.cardGame.getTopOfDeck());
		}
		this.players.get(1).addCardToHand(this.cardGame.getTopOfDeck());
	}
	
	public boolean update(){
		/*if(this.players.get(0).getHandSize() == 0){
			if(this.players.get(0).resetHand()){
				this.gameOver = true;
			}
		}
		if(this.players.get(1).getHandSize() == 0){
			if(this.players.get(1).resetHand()){
				this.gameOver = true;
			}
		}*/
		//p1Card = this.players.get(0).playTopCard();
		//p2Card = this.players.get(1).playTopCard();
		
		System.out.println(this.players.get(0).getHandSize()+"   "+this.players.get(1).getHandSize());
		if((p1Card = this.players.get(0).playTopCard()) == null){
			this.gameOver = true;
		}
		if((p2Card = this.players.get(1).playTopCard()) == null){
			this.gameOver = true;
		}
		
		this.winnings.push(p1Card);
		this.winnings.push(p2Card);
		
		this.score(p1Card, p2Card);
		
		if(gameOver){
			if(this.players.get(0).getHandSize() == 0){
				this.win(this.players.get(1));
				return false;
			} else {
				this.win(this.players.get(0));
				return false;
			}
		}
		return true;
	}

	public void play(){
		/*for(int i=1;i<=13;i++){
			this.players.get(0).addCardToHand(new Card(i,0));
		}
		for(int i=1;i<=13;i++){
			this.players.get(0).addCardToHand(new Card(i,1));
		}
		for(int i=1;i<=13;i++){
			this.players.get(0).addCardToHand(new Card(i,2));
		}
		for(int i=2;i<=13;i++){
			this.players.get(0).addCardToHand(new Card(i,3));
		}
		this.players.get(1).addCardToHand(new Card(1,3));
		*/
		
		for(int i=0;i<52;i++){
			if(i%2 == 0){
				this.players.get(0).addCardToHand(this.cardGame.getTopOfDeck());
			} else {
				this.players.get(1).addCardToHand(this.cardGame.getTopOfDeck());
			}
		}
		int j=0;
		Card p1Card = null;
		Card p2Card = null;
		while(!gameOver){
			j++;
			if(this.players.get(0).getHandSize() == 0){
				if(this.players.get(0).resetHand()){
					this.gameOver = true;
				}
			}
			if(this.players.get(1).getHandSize() == 0){
				if(this.players.get(1).resetHand()){
					this.gameOver = true;
				}
			}
			p1Card = this.players.get(0).playTopCard();
			p2Card = this.players.get(1).playTopCard();
			
			this.winnings.push(p1Card);
			this.winnings.push(p2Card);
			
			this.score(p1Card, p2Card);
		}
		System.out.println("Turns: "+j);
		if(this.players.get(0).getHandSize() == 0){
			this.win(this.players.get(1));
		} else {
			this.win(this.players.get(0));
		}
	}
}
