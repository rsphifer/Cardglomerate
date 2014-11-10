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
	private CardGame cardGame;
	private boolean gameOver = false;
	private int turn;
	private int expectedBet;
	private Player whoseTurn;
	public boolean readyToUpdate;
	private int playersGone = 0;
	private boolean handOver = false;
	
	public TexasHoldEm() {
		
	}
	
	public TexasHoldEm(ArrayList<Player> playerNames) {
		this.players = playerNames;
		winners = new ArrayList<Player>();
		cardsOnTable = new LinkedList<Card>();
		fillDeck();
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
	
	/*private void play(){
		switch(this.turn){
			case 0: //ante
				for(Player p : players){
					p.bet(50);
				}
				break;
			case 1: //deal 2
				for(Player p : players){
					p.addCardToHand(this.cardGame.getTopOfDeck());
					p.addCardToHand(this.cardGame.getTopOfDeck());
				}
				break;
			case 3: //burn and 3 out
				this.cardGame.getTopOfDeck();
				cardsOnTable.add(this.cardGame.getTopOfDeck());
				cardsOnTable.add(this.cardGame.getTopOfDeck());
				cardsOnTable.add(this.cardGame.getTopOfDeck());
				break;
			case 5: //burn and deal 1
			case 7: //burn and deal 1
				this.cardGame.getTopOfDeck();
				cardsOnTable.add(this.cardGame.getTopOfDeck());
				break;
			case 2: //bet
			case 4: //bet
			case 6: //bet
			case 8: //bet
				//promptForBets(winner, i);
				System.out.println("BETS, BETS, BETS BETS BETS BETS\n");
				break;
		}
	}
	*/
	
	public boolean updateReady(){
		if(turn == 0){
			return true;
		}
		for(Player p : players){
			if(p.getCurrentBet()!=expectedBet && p.isFolded() == false){
				readyToUpdate = false;
				return readyToUpdate;
			}
		}
		readyToUpdate = true;
		return readyToUpdate;
	}
	
	public void setBet(Player p, int bet){
		p.bet(bet);
		nextTurn(p);
		this.expectedBet = bet;
	}
	
	public void nextTurn(Player curP){
		int found = 0;
		for(Player p : players){
			if(found == 1){
				whoseTurn = p;
				break;
			}
			if(curP.userName == p.userName){
				found = 1;
			}
		}
		if(found == 1){
			players.get(0);
		}
	}
	
	public void fold(Player p){
		p.fold();
		playersGone++;
	}
	
	public void update(){
		System.out.println(turn);
		//int playersGone = 0;
		readyToUpdate = false;

		switch(this.turn){
			case 0: //ante
				handOver = false;
				for(Player p : players){
					p.bet(50);
				}
				for(Player p : players){
					p.addCardToHand(this.cardGame.getTopOfDeck());
					p.addCardToHand(this.cardGame.getTopOfDeck());
				}
				whoseTurn = players.get(0);
				readyToUpdate = true;
				break;
			case 1:
			case 3:
			case 5:
				readyToUpdate = false;
				/*while(!updateReady()){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}*/
				break;
			case 2: //burn and 3 out
				this.cardGame.getTopOfDeck();
				cardsOnTable.add(this.cardGame.getTopOfDeck());
				cardsOnTable.add(this.cardGame.getTopOfDeck());
				cardsOnTable.add(this.cardGame.getTopOfDeck());
				readyToUpdate = true;
				break;
			case 4: //burn and deal 1
			case 6: //burn and deal 1
				this.cardGame.getTopOfDeck();
				cardsOnTable.add(this.cardGame.getTopOfDeck());
				readyToUpdate = true;
				break;
		}
		
		/*if(turn != 3 && turn != 5 && turn != 7){
			readyToUpdate = true;
			//for(Player p : players){
				//whoseTurn = p;
				play();
			//}
		} else {
			readyToUpdate = true;
		}*/
		
		if(turn == 9){
			int j=0;
			HandValue[] values = new HandValue[this.players.size()];
			for(Player p : this.players){
				values[j] = new HandValue(p);
				j++;
			}
			
			getWinners(values);

			for(Player p : winners){
				p.addMoney(winnings/winners.size());
			}
			handOver = true;
		}
		
		if(turn == 3 || turn == 5 || turn == 7){
			for(Player p : players){
				winnings += p.getCurrentBet();
				p.bet(0);
			}
		}

		turn++;
		if(this.turn > 9){
			for(Player p : players){
				p.resetFold();
				p.emptyHand();
			}
			this.cardGame.fillDeckHoldEm();
			winners.clear();
			cardsOnTable.clear();
			winnings = 0;
			this.turn = 0;
		}
	}
	
	public void setup(){
		for(Player p : players){
			p.addCardToHand(this.getTopOfDeck());
			if(p.getMoney()<5000){
				p.addMoney(5000-p.getMoney());		// remove EVENTUALLY
			} else {
				p.addMoney(p.getMoney()*(-1));
				p.addMoney(5000);
			}
		}
		for(Player p : players){
			p.addCardToHand(this.getTopOfDeck());
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
