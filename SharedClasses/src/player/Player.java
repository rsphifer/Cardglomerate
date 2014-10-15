package player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Queue;

import cards.Card;


@SuppressWarnings("serial")
public class Player implements Serializable{

	private ArrayList<Player> friends;
	private Queue<Card> hand;
	private Queue<Card> discard;
	private int money;
	public String userName;
	private String password;
	
	public Player(String playerName, String password){
		this.password = password;
		this.userName = playerName;
	}

	public int getHandSize(){
		return this.hand.size();
	}
	
	public void addCardToHand(Card newCard){
		this.hand.add(newCard);
	}

	public Card playTopCard(){
		return this.hand.remove();
	}
}
