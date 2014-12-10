package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import misc.ChatEntry;
import misc.GameLobby;
import misc.GameLobbyRequest;
import model.Model;
import player.Friend;
import cardgames.BlackJack;
import cardgames.CardGame;
import cardgames.CardGameType;

public class UpdateChecker implements Runnable {
	private final int MILLIS_BETWEEN_UPDATES_IN_GAME = 1500;
	private final int MILLIS_BETWEEN_UPDATES = 5000;

	private Model model;

	public UpdateChecker(Model model) {
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while (model.isLoggedIn) {

			/* Sleep between server calls to minimize busy waiting */
			try {
				if (model.isInGame) {
					Thread.sleep(MILLIS_BETWEEN_UPDATES_IN_GAME);
				} else {
					Thread.sleep(MILLIS_BETWEEN_UPDATES);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"An error with the server has occurred.", null,
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}

			/* Update model with newest game, chat, and friend states */
			// System.out.println("Ask server for updates.");

			Object obj;

			if (model.isInGame) {
				obj = ServerAccess.getCardGame(model.getGameId());
				if (obj != null) {
					model.setCurrentGame((CardGame) obj);
				}
			} else {

				/* Update friends list */
				obj = ServerAccess.getFriends(model.getPlayer());
				if (obj != null) {
					model.setFriendList((ArrayList<Friend>) obj);
				}

				/* Update menu chat */
				obj = ServerAccess.getMenuChat();
				if (obj != null) {
					model.setMenuChat((ArrayList<ChatEntry>) obj);
				}

				/* Update game lobbies */
				obj = ServerAccess.getGameLobbyList(new GameLobbyRequest(null,
						0, CardGameType.War));
				if (obj != null) {
					model.setGameLobby(CardGameType.War, (GameLobby[]) obj);
				}

				obj = ServerAccess.getGameLobbyList(new GameLobbyRequest(null,
						0, CardGameType.ERS));
				if (obj != null) {
					model.setGameLobby(CardGameType.ERS, (GameLobby[]) obj);
				}

				obj = ServerAccess.getGameLobbyList(new GameLobbyRequest(null,
						0, CardGameType.TexasHoldEm));
				if (obj != null) {
					model.setGameLobby(CardGameType.TexasHoldEm,
							(GameLobby[]) obj);
				}

				/* Update blackjack tables */
				obj = ServerAccess.getBlackJackTables();
				if (obj != null) {
					model.setBlackJackTables((BlackJack[]) obj);
				}
			}

		}

		return;

	}

}
