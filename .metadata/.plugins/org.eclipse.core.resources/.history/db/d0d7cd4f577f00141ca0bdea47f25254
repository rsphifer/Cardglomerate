package player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import cards.Card;


@SuppressWarnings("serial")
public class Player implements Serializable{
	
	private int playerId;
	
	private ArrayList<Card> hand;
	private ArrayList<Card> discard;
	private int money = 0;
	public String userName;
	private int currentBet = 0;
	private boolean folded = false;
	
	public Player(String playerName){
		this.hand = new ArrayList<Card>();
		this.discard = new ArrayList<Card>();
		this.userName = playerName;
	}
	
	public void setPlayerId(int id) {
		this.playerId = id;
	}
	
	public int getPlayerId() {
		return playerId;
	}

	public int getHandSize(){
		return (this.hand.size()+this.discard.size());
	}
	
	public ArrayList<Card> getHand(){
		return this.hand;
	}
	
	public void emptyHand(){
		this.hand.clear();
	}
	
	public int getDiscardSize(){
		return this.discard.size();
	}
	
	public void addMoney(int money){
		this.money += money;
	}
	
	public int getMoney(){
		return this.money;
	}
	
	public void bet(int amount){
		this.currentBet = amount;
	}
	
	public int getCurrentBet(){
		return this.currentBet;
	}
	
	public void resetFold(){
		folded = false;
	}
	
	public void fold(){
		folded = true;
	}
	
	public boolean isFolded(){
		return folded;
	}
	
	public void addCardToDiscard(Card newCard){
		this.discard.add(newCard);
	}
	
	public boolean resetHand(){
		if(this.discard.isEmpty()){
			System.out.println(this.userName+"'s discard is empty");
			return true;
		}
		Collections.shuffle(this.discard);
		for(Card c : this.discard){
			this.hand.add(c);
		}
		this.discard.clear();
		System.out.println(this.userName+" reset their hand!");
		System.out.println(this.userName+" has "+this.getHandSize());
		return false;
	}
	
	public void addCardToHand(Card newCard){
		this.hand.add(newCard);
	}

	public Card playTopCard(){
		if(this.hand.isEmpty()){
			if(resetHand()){
				return null;
			}
		}
		return this.hand.remove(0);
	}
}
