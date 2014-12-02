package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import player.Player;
import HandEvaluator.HandValue;
import cards.Card;

public class TexasHoldEm extends CardGame implements Serializable{

	private ArrayList<Player> players;
	private ArrayList<Player> winners;
	private int winnings;
	private LinkedList<Card> cardsOnTable;
	private boolean gameOver = false;
	private int turn = 0;
	private int expectedBet;
	private Player whoseTurn;
	public boolean readyToUpdate;
	private int playersGone = 0;
	private boolean handOver = false;

	public TexasHoldEm(ArrayList<Player> playerNames) {
		players = new ArrayList<Player>();
		for (int i=0; i<playerNames.size(); i++) {
			players.add(playerNames.get(i));
		}
		
		whoseTurn = players.get(0);
		winners = new ArrayList<Player>();
		cardsOnTable = new LinkedList<Card>();
		fillDeck();
		setup();
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
	
	public boolean updateReady(){
		if(turn == 0 || turn == 2 || turn == 4 || turn == 6){
			return true;
		}
		for(int i=0;i<players.size();i++){
			if(players.get(i).getCurrentBet()!=expectedBet && players.get(i).isFolded() == false){
				readyToUpdate = false;
				return false;
			}
		}
		readyToUpdate = true;
		return true;
	}
	
	public void setBet(Player p, int bet){
		System.out.println(p.userName+" is betting "+bet);
		p.bet(bet);
		nextTurn(p);
		this.expectedBet = bet;
	}
	
	public void nextTurn(Player curP){
		int found = 0;
		for(int i=0;i<players.size();i++){
			if(found == 1){
				whoseTurn = players.get(i);
				break;
			}
			if(curP.userName.equals(players.get(i).userName)){
				found = 1;
			}
		}
		if(found == players.size()){
			players.get(0);
		}
		System.out.println("next turn function   "+whoseTurn.userName);
	}
	
	public void fold(Player p){
		p.fold();
		playersGone++;
	}
	
	public void update(){
		System.out.println(turn);
		readyToUpdate = false;

		switch(turn){
			case 0: //ante
				//System.out.println("step 0");
				handOver = false;
				for(int i=0;i<players.size();i++){
					players.get(i).bet(50);
				}
				for(int i=0;i<players.size();i++){
					players.get(i).addCardToHand(this.getTopOfDeck());
					players.get(i).addCardToHand(this.getTopOfDeck());
					//System.out.println(players.get(i).userName);
					//System.out.println(players.get(i).getHand().get(0).getPower() + " of "+players.get(i).getHand().get(0).getSuit());
					//System.out.println(players.get(i).getHand().get(1).getPower() + " of "+players.get(i).getHand().get(1).getSuit());
				}
				whoseTurn = players.get(0);
				readyToUpdate = true;
				turn++;
				//System.out.println("step 0 end at turn "+turn);
				break;
			case 1:
			case 3:
			case 5:
				System.out.println("step bet");
				readyToUpdate = false;
				turn++;
				/*while(!updateReady()){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}*/
				break;
			case 2: //burn and 3 out
				System.out.println("step deal 3");
				this.getTopOfDeck();
				cardsOnTable.add(this.getTopOfDeck());
				cardsOnTable.add(this.getTopOfDeck());
				cardsOnTable.add(this.getTopOfDeck());
				readyToUpdate = true;
				turn++;
				System.out.println("step deal 3 end");
				break;
			case 4: //burn and deal 1
			case 6: //burn and deal 1
				System.out.println("step deal 1");
				this.getTopOfDeck();
				cardsOnTable.add(this.getTopOfDeck());
				readyToUpdate = true;
				turn++;
				System.out.println("step deal 1 end");
				break;
		}

		if(turn == 3 || turn == 5 || turn == 7){
			for(int i=0;i<players.size();i++){
				winnings += players.get(i).getCurrentBet();
				players.get(i).bet(0);
			}
		}
		
		if(turn == 8){
			int j=0;
			HandValue[] values = new HandValue[this.players.size()];
			for(int i=0;i<players.size();i++){
				values[j] = new HandValue(players.get(i));
				j++;
			}
			
			getWinners(values);

			for(int i=0;i<winners.size();i++){
				winners.get(i).addMoney(winnings/winners.size());
			}
			handOver = true;
			
			for(int i=0;i<players.size();i++){
				players.get(i).resetFold();
				players.get(i).emptyHand();
			}
			this.fillDeckHoldEm();
			winners.clear();
			cardsOnTable.clear();
			winnings = 0;
			this.turn = 0;
		}
	}
	
	public void setup(){
		for(int i=0;i<players.size();i++){
			players.get(i).addCardToHand(this.getTopOfDeck());
			if(players.get(i).getMoney()<5000){
				players.get(i).addMoney(5000-players.get(i).getMoney());		// remove EVENTUALLY
			} else {
				players.get(i).addMoney(players.get(i).getMoney()*(-1));
				players.get(i).addMoney(5000);
			}
		}
		for(int i=0;i<players.size();i++){
			players.get(i).addCardToHand(this.getTopOfDeck());
		}
		this.turn=0;
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
