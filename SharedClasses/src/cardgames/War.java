package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

import player.Player;
import cards.Card;

@SuppressWarnings("serial")
public class War extends CardGame implements Serializable{

	public War(ArrayList<Player> playerNames) {
		super(playerNames);
	}

	private ArrayList<Player> players;
	private Stack<Card> winnings;
	


	private void roundWin(Player winner){
		while(!this.winnings.empty()){
			winner.addCardToHand((Card)this.winnings.remove(0));
		}
	}

	private void roundTie(){
		//model.war();
		
		Card p1Down = this.players.get(0).playTopCard();
		this.winnings.push(p1Down);
		Card p1Up = this.players.get(0).playTopCard();
		this.winnings.push(p1Up);
		
		Card p2Down = this.players.get(1).playTopCard();
		this.winnings.push(p2Down);
		Card p2Up = this.players.get(1).playTopCard();
		this.winnings.push(p2Up);
		
		//Thread.sleep(3000);

		this.score(p1Up,p2Up);
	}

	private void score(Card c1, Card c2){
		//model.displayThese2Cards(c1,c2);
		
		if(c1.getPower() > c2.getPower()){
			roundWin(this.players.get(0));
		} else if(c1.getPower() < c2.getPower()){
			roundWin(this.players.get(1));
		} else {
			roundTie();
		}
	}

	private void win(Player winner){
		//model.win(winner.userName);
		System.out.println(winner.userName+" won!");
	}

	public void play(){

		while(this.players.get(0).getHandSize() != 0 || this.players.get(1).getHandSize() != 0){
			Card p1Card = this.players.get(0).playTopCard();
			this.winnings.push(p1Card);
			Card p2Card = this.players.get(0).playTopCard();
			this.winnings.push(p2Card);
			//while(model.isWaiting()){
			//	Thread.sleep(1000);
			//}
			this.score(p1Card, p2Card);
		}
		if(this.players.get(0).getHandSize() == 0){
			this.win(this.players.get(1));
		} else {
			this.win(this.players.get(0));
		}
	}
}
