package player;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import cards.Card;


@SuppressWarnings("serial")
public class Player implements Serializable{

	private ArrayList<Player> friends;
	private ArrayDeque<Card> hand;
	private ArrayList<Card> discard;
	private int money;
	public String userName;
	private String password;
	
	public Player(String playerName, String password){
		this.hand = new ArrayDeque<Card>();
		this.discard = new ArrayList<Card>();
		this.password = password;
		this.userName = playerName;
	}

	public int getHandSize(){
		return this.hand.size();
	}
	
	public void getMoney(int money){
		this.money += money;
	}
	
	public void addCardToHand(Card newCard){
		System.out.println(this.userName+" got a "+newCard.getPower() + " of " + newCard.getSuit());
		this.hand.add(newCard);
	}

	public Card playTopCard(){
		if(!this.hand.isEmpty()){
			return this.hand.remove();
		} else {
			return null;
		}
	}
}
