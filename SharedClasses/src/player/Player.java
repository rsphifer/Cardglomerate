package player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import cards.Card;


@SuppressWarnings("serial")
public class Player implements Serializable{

	private ArrayList<Player> friends;
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

	public int getHandSize(){
		return this.hand.size();
	}
	
	public int getDiscardSize(){
		return this.discard.size();
	}
	
	public void getMoney(int money){
		this.money += money;
	}
	
	public void addCardToDiscard(Card newCard){
		//System.out.println(this.userName+" got a "+newCard.getPower() + " of " + newCard.getSuit());
		this.discard.add(newCard);
	}
	
	public boolean resetHand(){
		if(this.discard.isEmpty()){
			return true;
		}
		Collections.shuffle(this.discard);
		this.hand = this.discard;
		this.discard.clear();
		System.out.println(this.userName+" reset their hand!");
		return false;
	}
	
	public void addCardToHand(Card newCard){
		//System.out.println(this.userName+" got a "+newCard.getPower() + " of " + newCard.getSuit());
		this.hand.add(newCard);
	}

	public Card playTopCard(){
		if(!this.hand.isEmpty()){
			return this.hand.remove(0);
		} else {
			if(this.resetHand()){
				System.out.println(this.userName+" should have lost");
				return null;
			} else {
				return this.playTopCard();
			}
		}
	}
}
