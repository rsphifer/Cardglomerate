package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import HandEvaluator.HandValue;
import player.Player;
import cards.Card;

public class TexasHoldEm extends CardGame implements Serializable{

	private ArrayList<Player> players;
	private ArrayList<Player> winners;
	private int winnings;
	private LinkedList<Card> cardsOnTable;
	private Player winner;
	private CardGame cardGame;
	private boolean gameOver = false;
	private int turn;
	
	public TexasHoldEm() {
		
	}
	
	public TexasHoldEm(ArrayList<Player> playerNames) {
		
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
	
	private void promptForBets(Player p, int amount){
		winnings += p.bet(amount);
	}
	
	public void update(){
		this.turn=0;
		
		switch(this.turn){
			case 0: //ante
				for(Player p : this.players){
					this.winnings += p.bet(50);	
				}
				break;
			case 1: //deal 2
				for(Player p : this.players){
					p.addCardToHand(this.cardGame.getTopOfDeck());
				}
				for(Player p : this.players){
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
				System.out.println("BETS BETS BETS BETS BETS BETS\n");
				break;
			case 9: //find winners and give out money
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
				winnings = 0;
				cardsOnTable.clear();
				for(Player p : winners){
					p.emptyHand();
				}
				break;
		}
		
		this.turn++;
		if(this.turn > 9){
			this.turn = 0;
		}
	}
	
	public void setup(){
		for(int j=0;j<4;j++){
			for(int i=0;i<52;i++){
				// fill decks
			}
		}
	}
}