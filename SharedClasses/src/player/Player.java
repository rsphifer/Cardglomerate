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
	private int money;
	public String userName;
	private String password;
	
	public Player(String playerName, String password){
		this.hand = new ArrayList<Card>();
		this.discard = new ArrayList<Card>();
		this.password = password;
		this.userName = playerName;
	}
	
	public void setPlayerId(int id) {
		this.playerId = id;
	}
	
	public int getPlayerId() {
		return playerId;
	}

	public int getHandSize(){
		return this.hand.size();
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
	
	public int bet(int amount){
		money -= amount;
		return amount;
	}
	
	public void addCardToDiscard(Card newCard){
		//System.out.println(this.userName+" got a "+newCard.getPower() + " of " + newCard.getSuit());
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
		//System.out.println(this.userName+" got a "+newCard.getPower() + " of " + newCard.getSuit());
		this.hand.add(newCard);
	}

	public Card playTopCard(){
		if(this.hand.isEmpty()){
			if(resetHand()){
				return null;
			}
		} //else {
		return this.hand.remove(0);
			/*if(this.getDiscardSize() == 0){
				return null;
			} else {
				System.out.println("the fuck?!?");
				return null;
			}*/
		//}
	}
}
