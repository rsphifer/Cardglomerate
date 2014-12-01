package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import player.Player;
import cards.Card;

public class BlackJack extends CardGame implements Serializable {
	
	private ArrayList<Card> dealerHand;
	int dealerStay;
	private ArrayList<Card> playerHand;
	int playerStay;

	public BlackJack(){
		dealerHand = new ArrayList<Card>();
		playerHand = new ArrayList<Card>();
		playerStay = 0;
		dealerStay = 0;
		fillDeck();
	}
	
	public void dealCards(){
		playerHand.add(getTopOfDeck());
		dealerHand.add(getTopOfDeck());
		playerHand.add(getTopOfDeck());
		dealerHand.add(getTopOfDeck());
	}
	
	public void seePlayerHand(){
		int sizeOfHand = playerHand.size();
		for(int i = 0; i < sizeOfHand;i++){
			System.out.println("Card is Suit ="+playerHand.get(i).getSuit()+"Power"+playerHand.get(i).getPower());
		}	
		System.out.println("Your combined hand is = to "+getPlayerScoreWithAces());
	}
	
	public void seeDealerHand(){
		int sizeOfHand = dealerHand.size();
		for(int i = 0; i < sizeOfHand;i++){
			System.out.println("Card is Suit ="+dealerHand.get(i).getSuit()+"Power"+dealerHand.get(i).getPower());
		}	
		System.out.println("Your combined hand is = to "+getDealerScoreWithAces());
	}
	
	public int getPlayerScore(){
		int sizeOfHand = playerHand.size();
		int handScore = 0;
		int power;
		for(int i = 0; i < sizeOfHand;i++){
			power = playerHand.get(i).getPower();
			if(power > 10){
				handScore += 10;
			}
			else if(power == 1){
				handScore += 11;
			}
			else
				handScore += power;
		}
		return handScore;
	}
	
	public int getPlayerScoreWithAces(){
		if(getPlayerScore() < 22){
			return getPlayerScore();
		}
		int numAces = 0;
		int sizeOfHand = playerHand.size();
		int power;
		for(int i = 0; i < sizeOfHand;i++){
			power = playerHand.get(i).getPower();
			if(power == 1){
				numAces += 1;
			}
		}
		int newScore = getPlayerScore();
		while(numAces != 0){
			newScore -= 10;
			numAces--;
			if(newScore <= 21){
				return newScore;
			}
		}
		return newScore;
	}
	
	public int getDealerScore(){
		int sizeOfHand = dealerHand.size();
		int handScore = 0;
		int power;
		for(int i = 0; i < sizeOfHand;i++){
			power = dealerHand.get(i).getPower();
			if(power > 10){
				handScore += 10;
			}
			else if(power == 1){
				handScore += 11;
			}
			else
				handScore += power;
		}
		return handScore;
	}
	
	public int getDealerScoreWithAces(){
		if(getDealerScore() < 22){
			return getDealerScore();
		}
		int numAces = 0;
		int sizeOfHand = dealerHand.size();
		int power;
		for(int i = 0; i < sizeOfHand;i++){
			power = dealerHand.get(i).getPower();
			if(power == 1){
				numAces += 1;
			}
		}
		int newScore = getDealerScore();
		while(numAces != 0){
			newScore -= 10;
			numAces--;
			if(newScore <= 21){
				return newScore;
			}
		}
		return newScore;
	}
	
	public void dealerPlay(){
		if(getPlayerScoreWithAces() <= 21 && getDealerScoreWithAces() <= 21){
			int dealerScore = getDealerScoreWithAces();
			if(dealerScore < 15){
				dealerHand.add(getTopOfDeck());
			}
			else
				dealerStay = 1;
		}
		else
			dealerStay = 1;
	}
	
	public void playerPlay(){
		int breaker = 0;
		if(getPlayerScoreWithAces() <= 21){
			if(playerStay != 1){
				seePlayerHand();
				System.out.println();
				int valueEntered = 0;
				while(breaker == 0){
					System.out.println("To Hit Press 1, To Stay press 2");
					Scanner player1Input = new Scanner( System.in );
					valueEntered = player1Input.nextInt();
					if(valueEntered == 1){
						System.out.println("value entered = "+valueEntered);
						playerHand.add(getTopOfDeck());
						breaker = 1;
					}
					else if(valueEntered == 2){
						System.out.println("value entered = "+valueEntered);
						playerStay = 1;
						breaker = 1;
					}
				}
			}
		}
		else 
			playerStay = 1;
	}
	
	public void dealerVsPlayerPlay(){
		while(playerStay != 1){
			playerPlay();
		}
		while(dealerStay != 1){
			dealerPlay();
		}
		
	}
	
	
	public void playBlackJack(){
		dealCards();
		dealerVsPlayerPlay();
		int result = gameWinner();
		if(result == 1){
			System.out.println("The Dealer won with the cards:");
			seeDealerHand();
			System.out.println("The player had these cards:");
			seePlayerHand();
		}
		else if(result == 2){
			System.out.println("The Player won with the cards:");
			seePlayerHand();
			System.out.println("The Dealer had these cards:");
			seeDealerHand();
		}
	}
	
	//return 1 if the dealer won, and 2 if the player won
	public int gameWinner(){
		int playerScore = getPlayerScoreWithAces();
		int dealerScore = getDealerScoreWithAces();
		
		if(playerScore > 21){
			return 1;
		}
		else if(playerScore == dealerScore){
			return 1;
		}
		else if(dealerScore > playerScore && dealerScore <= 21){
			return 1;
		}
		else
			return 2;
	}
	
	
	public static void main(String[] args){
		BlackJack test1 =  new BlackJack();
		test1.playBlackJack();
		//test1.playBlackJack();
		//test1.seePlayerHand();
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testSetup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Player> getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Integer> getHandSizes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getGameover() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<Card> getCardsToDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPot() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getExpectedBet() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBet(Player p, int bet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextTurn(Player curP) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fold(Player p) {
		// TODO Auto-generated method stub
		
	}
}

