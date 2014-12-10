package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import player.Player;
import HandEvaluator.HandValue;
import cards.Card;

public class ERS extends CardGame implements Serializable{

	private ArrayList<Player> players;
	private ArrayList<Player> winners;
	private int winnings = 0;
	private LinkedList<Card> cardsOnTable;
	private boolean gameOver = false;
	private int turn = 0;
	private int expectedBet = 0;
	private Player whoseTurn;
	public boolean readyToUpdate;
	private int playersGone = 0;
	private boolean handOver = false;
	private int clickCounter = 0;
	
	public int cardsToPlay;

	public ERS(ArrayList<Player> playerNames) {
		players = new ArrayList<Player>();
		for (int i=0; i<playerNames.size(); i++) {
			players.add(playerNames.get(i));
		}
		System.out.println("playersize: "+players.size());
		whoseTurn = players.get(0);
		winners = new ArrayList<Player>();
		cardsOnTable = new LinkedList<Card>();
		fillDeck();
		setup();
		
		handOver = false;
		for(int i=0;i<players.size();i++){
			players.get(i).bet(50);
			winnings += players.get(i).getCurrentBet();
			players.get(i).bet(0);
		}
		for(int i=0;i<players.size();i++){
			players.get(i).addCardToHand(this.getTopOfDeck());
			players.get(i).addCardToHand(this.getTopOfDeck());
		}
		readyToUpdate = true;
		turn++;
		
		//update();
	}
	
	private void getWinners(HandValue[] values){
		int index=-1;
		int max=-1;
		int tie=-1;
		int tie2=-1;
		int tie3=-1;
		for(int i=0;i<values.length;i++){
			if(max < values[i].getValue()){
				tie=0; tie2=0; tie3=0;
				max = values[i].getValue();
				index = i;
			} else if(max == values[i].getValue()){
				if(tie3 >= 0){
					if(tie2 >= 0){
						tie = i;
					}
					tie2 = i;
				}
				tie3 = i;
			}
		}
		if(tie3>=0){
			winners.add(players.get(tie3));
			if(tie2>=0){
				winners.add(players.get(tie2));
				if(tie>=0){
					winners.add(players.get(tie));
				}
			}
		}
		winners.add(players.get(index));
	}
	
	public void setCardsToPlay(){
		
		Card lastCard = cardsOnTable.peekFirst();
		if(lastCard == null){
			cardsToPlay = -1;
		}
		switch(lastCard.getPower()){
		case 1:
			cardsToPlay=4;
			break;
		case 11:
			cardsToPlay=1;
			break;
		case 12:
			cardsToPlay=2;
			break;
		case 13:
			cardsToPlay=3;
			break;
		}
		return;
	}
	
	public boolean updateReady(){
		if(turn == 0 || turn == 2 || turn == 4 || turn == 6){
			return true;
		}
		for(int i=0;i<players.size();i++){
			System.out.println(players.get(i).getCurrentBet() + " " + expectedBet);
			if(players.get(i).getCurrentBet() != expectedBet && players.get(i).isFolded() == false){
				//readyToUpdate = false;
				return false;
			}
		}
		//if(readyToUpdate == false){
			System.out.println("betting round over");
			for(int i=0;i<players.size();i++){
				winnings += players.get(i).getCurrentBet();
				players.get(i).bet(0);
			}
			turn++;
			update();
			expectedBet = 0;
		//}
		readyToUpdate = true;
		return true;
	}
	
	public void setBet(Player p, int bet){
		System.out.println(p.userName+" is betting "+bet);
		p.bet(bet);
		this.expectedBet = bet;
		updateReady();
		nextTurn(p);
	}
	
	public void nextTurn(Player curP){
		int found = 0;
		int i=0;
		for(i=0;i<players.size();i++){
			if(found == 1){
				whoseTurn = players.get(i);
				break;
			}
			if(curP.userName.equals(players.get(i).userName)){
				found = 1;
			}
		}
		if(i == players.size()){
			whoseTurn = players.get(0);
		}
		setCardsToPlay();
		System.out.println("next turn function   "+whoseTurn.userName);
	}
	
	public Player lastTurn(){
		
		Player lplayer = players.get(players.size()-1);
		
		for(int i=0;i<players.size();i++){
			if(whoseTurn.userName.equals(players.get(i).userName)){
				lplayer = players.get(i);
			}
		}
		
		return lplayer;
		
	}
	
	public synchronized void incrementClickCounter() {
		clickCounter++;
		update();
	}
	
	public int getClickCounter() {
		return clickCounter;
	}
	
	public void fold(Player p){
		p.fold();
		playersGone++;
	}
	
	public void update(){
		//if(updateReady()){
		System.out.println(turn);
		readyToUpdate = false;
		
		if(clickCounter > 0){
			
			clickCounter = 0;
			
			Card playedcard;
			
			if(cardsToPlay == -1){
				if(whoseTurn.playTopCard() == null){
					gameOver = true;
				}
			}
			
			
			else{
				while(cardsToPlay > 0){
					if((playedcard = whoseTurn.playTopCard()) == null){
						gameOver = true;
					}
					if(playedcard.getPower() >= 11 || playedcard.getPower() ==1){
						break;
					}
					cardsToPlay--;
				}
				if(cardsToPlay == 0){
					roundWin(lastTurn());
				}
			}
		}
		
	}
	
	private void roundWin(Player winner) {
		while (!cardsOnTable.isEmpty()) {
			winner.addCardToDiscard((Card) cardsOnTable.remove(0));
		}
	}
	
	public void setup(){
			for (int i = 0; i < 52; i++) {
				if (i % 2 == 0) {
					players.get(0).addCardToHand(getTopOfDeck());
				} else {
					players.get(1).addCardToHand(getTopOfDeck());
				}
		}
	}

	@Override
	public void testSetup() {
		
	}

	@Override
	public ArrayList<Player> getWinner() {
		return winners;
	}

	@Override
	public LinkedList<Integer> getHandSizes() {
		return null;
	}

	@Override
	public boolean getGameover() {
		return this.gameOver;
	}

	@Override
	public LinkedList<Card> getCardsToDisplay() {
		return cardsOnTable;
	}
	
	public int getPot(){
		return this.winnings;
	}
	
	public int getExpectedBet(){
		return expectedBet;
	}
	
	public boolean isHandOver(){
		return this.handOver;
	}
	
	public void quitGame(Player p) {
		players.remove(p);
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public Player getTurn(){
		return whoseTurn;
	}
}
