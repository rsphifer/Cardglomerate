package main;

import java.util.ArrayList;

import misc.ChatEntry;

public class MenuChat {
	private static final int MAX_CHAT_ENTRIES = 16;

	private ArrayList<ChatEntry> chatLog;
	
	public MenuChat() {
		chatLog = new ArrayList<ChatEntry>();
	}
	
	public ArrayList<ChatEntry> getChatLog() {
		return chatLog;
	}
	
	public synchronized void addChatEntry(ChatEntry newEntry) {
		
		/* Insert message at front of list */
		chatLog.add(chatLog.size(), newEntry);
	
		/* Remove oldest messages until under max entries allowed */
		while (chatLog.size() > MAX_CHAT_ENTRIES) {
			chatLog.remove(0);
		}
		
	}
	
	

}
