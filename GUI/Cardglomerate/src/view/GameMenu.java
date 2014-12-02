package view;

import java.util.ArrayList;
import java.util.LinkedList;

import misc.ChatEntry;
import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cardgames.CardGameType;
import player.Friend;

public class GameMenu extends BasicGameState {
 private Model model;
 

 private String mouse = "No input yet";
 private Image background;
 private Image ratscrew;
 private Image fish;
 private Image holdEm;
 private Image stud;
 private Image blackjack;
 private Image war;
 
 //friends list crap
 private int curx;
 private int cury;
 private TextField friendField;
 private boolean friendSelected = false;
 ArrayList<Friend> friends;
 
 //chat stuff
 private int curChatx;
 private int curChaty;
 private TextField chatField;
 private boolean chatSelected = false;
 private String newMessage;
 private String temp;
 ArrayList<ChatEntry> menuChat;
 private boolean chatError = false;
 private int userNameLength;
 
 // card images for game GUI
 public static Image[] clubs;
 public static Image[] hearts;
 public static Image[] spades;
 public static Image[] diamonds;
 public static Image deckImage;

 public GameMenu(int state, Model model) {
  this.model = model;
 }

 public void init(GameContainer gc, StateBasedGame sbg)
   throws SlickException {
  
  // initialize and scale background image
  background = new Image("res/Green Background.jpg");
  background = background.getScaledCopy(1280, 720);

  // initialize and scale each game image
  ratscrew = new Image("res/rat.jpg");
  ratscrew = ratscrew.getScaledCopy(250, 250);

  fish = new Image("res/fish.jpg");
  fish = fish.getScaledCopy(250, 250);

  holdEm = new Image("res/Just Cards.jpg");
  holdEm = holdEm.getScaledCopy(250, 250);

  stud = new Image("res/Cards and Chips.jpg");
  stud = stud.getScaledCopy(250, 250);

  blackjack = new Image("res/Cards and Chips 2.jpg");
  blackjack = blackjack.getScaledCopy(250, 250);

  war = new Image("res/Many Cards.jpg");
  war = war.getScaledCopy(250, 250);

  // initialize flat deck image
  deckImage = new Image("res/Cards/b2fv.png");

  // initalize deck arrays
  clubs = new Image[13];
  hearts = new Image[13];
  spades = new Image[13];
  diamonds = new Image[13];
  for (int i = 1; i < 14; i++) {
   clubs[i - 1] = new Image("res/Cards/c" + i + ".png");
  }

  for (int i = 1; i < 14; i++) {
   hearts[i - 1] = new Image("res/Cards/h" + i + ".png");
  }

  for (int i = 1; i < 14; i++) {
   spades[i - 1] = new Image("res/Cards/s" + i + ".png");
  }

  for (int i = 1; i < 14; i++) {
   diamonds[i - 1] = new Image("res/Cards/d" + i + ".png");
  }
  
  //textfields
  friendField = new TextField(gc, gc.getDefaultFont(), 1070, 635, 200, 30);
  chatField = new TextField(gc, gc.getDefaultFont(), 5, 680, 250, 30);
 }

 public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
   throws SlickException {
  // render the background
  background.draw(0, 0);

  // mouse coordinates for testing / building
  g.drawString(mouse, 10, 25);

  //log out button
  g.drawRect(860, 670, 200, 40);
  g.drawString("Log Out", 920, 680);

  // welcome message
  g.drawString(
    "Welcome to Cardglomerate! Please Select a game (click image).",
    400, 10);

  // render each game image on the screen
  stud.draw(60, 75);
  blackjack.draw(360, 75);
  war.draw(660, 75);
  holdEm.draw(960, 75);
  ratscrew.draw(360, 375);
  fish.draw(660, 375);

  // render game names above images (probably temporary)
  g.drawString("Five Card Stud", 100, 50);
  g.drawString("Blackjack", 400, 50);
  g.drawString("War", 700, 50);
  g.drawString("Texas Hold'em", 1000, 50);
  g.drawString("Egyptian Ratscrew", 400, 350);
  g.drawString("Go Fish", 700, 350);

  // Account Options Button
  g.drawRect(1070, 670, 200, 40);
  g.drawString("Account Options", 1100, 680);
  
  //friends list rendering (limit = 14 friends with current spacing)
  g.drawString("Add Friend", 970, 640);
  g.drawRect(965, 635, 100, 30);
  g.drawString("Delete Frd", 865, 640);
  g.drawRect(860, 635, 100, 30);
  friendField.render(gc, g);
  g.drawString("Friends List", 1030, 330);
  curx = 960;
  cury = 350;
  
  if (model.isLoggedIn) {
	g.drawString("Stats", 1050, 500);
	g.drawString("War", 1010, 520);
	g.drawString("Blackjack", 1060, 520);
	g.drawString("Games", 930, 540);
	g.drawString("Wins", 930, 560);
	g.drawString("Ratio", 930, 580);
	  
	g.drawString(Integer.toString(model.getPlayerGameNumber(CardGameType.War)), 1010,540);
	g.drawString(Integer.toString(model.getPlayerWinNumber(CardGameType.War)), 1010,560);
	String formattedRatio = String.format("%.2f", model.getPlayerWinRatio(CardGameType.War));
	g.drawString(formattedRatio, 1010,580);
	
	g.drawString(Integer.toString(model.getPlayerGameNumber(CardGameType.Blackjack)), 1060,540);
	g.drawString(Integer.toString(model.getPlayerWinNumber(CardGameType.Blackjack)), 1060,560);
	formattedRatio = String.format("%.2f", model.getPlayerWinRatio(CardGameType.Blackjack));
	g.drawString(formattedRatio, 1060,580);
	  
   friends = model.getFriendsList();
   for (int i = 0; i < friends.size(); i++) {
    Friend tmp = friends.get(i);
    g.drawString(tmp.getUsername(), curx, cury);
    Color currColor = g.getColor();
    if (tmp.isOnline) {
     g.setColor(Color.blue);
    } else {
     g.setColor(Color.red);
    }
    g.fill(new Circle(curx-7, cury+10, 5));
    g.setColor(currColor);
    cury += 20;
   }
  }
    
  //chat rendering
  // limit: 16 chat messages of length 48
  g.drawString("Chat", 150, 330);
  g.drawRect(260, 680, 120, 30);
  g.drawString("Send Message", 265, 685);
  chatField.render(gc, g);
  curChatx = 5;
  curChaty = 350;
  
  menuChat = model.getMenuChat();
  for (int i = 0; i < menuChat.size(); i++) {
   ChatEntry currMessage = menuChat.get(i);
   g.drawString(currMessage.getUsername() + ": " + currMessage.getMessage(), curChatx, curChaty);
   //g.drawString(currMessage.getMessage(), curChatx+90, curChaty);
   curChaty += 20;
   
  }
  
  //chat error
  if (chatError){
   g.drawString("Message too long", 390, 685);
   if(!chatError) {
    g.clear();
   }
  }
 }

 public void update(GameContainer gc, StateBasedGame sbg, int delta)
   throws SlickException {
  // mouse coordinates
  int xpos = Mouse.getX();
  int ypos = Mouse.getY();
  mouse = "Mouse position x: " + xpos + " y: " + ypos;
  
  //enter functionality
  Input in = gc.getInput();
  boolean enterHit = false;
  if (in.isKeyPressed(Input.KEY_ENTER)) {
   enterHit = true;
  }
  
  //textfield crap
  if (friendSelected) {
   friendField.setFocus(true);
  }
  if (chatSelected) {
   chatField.setFocus(true);
  }

  //add friend textbox clicked
  if((xpos>1070 && xpos<1270) && (ypos>55 && ypos<85)) {
   if(Mouse.isButtonDown(0)) { //button 0 = left click
    chatSelected = false;
    friendSelected = true;
   }
  }
  
  //chat textbox clicked
  if((xpos>5 && xpos<255) && (ypos>10 && ypos<40)) {
   if(Mouse.isButtonDown(0)) { //button 0 = left click
    friendSelected = false;
    chatSelected = true;
    
   }
  }
  
  
  //add friend button clicked
  if(((xpos>965 && xpos<1065) && (ypos>50 && ypos<85)) || (enterHit && friendSelected)) {
   if((Mouse.isButtonDown(0) && Master.isMouseReleased) || (enterHit && friendSelected)) {
    Master.isMouseReleased = false;
    if (friendField.getText().length() > 1) {
     //code to add friend goes here
     model.addFriend(friendField.getText());
     
     //filler code for now
     //Master.friends.add(friendField.getText());
     friendField.setText(""); 
    }
   }
   
   if (!Mouse.isButtonDown(0)){
    Master.isMouseReleased = true;
   }
  }
  
  //delete friend button clicked
  if((xpos>860 && xpos<960) && (ypos>50 && ypos<85)) {
   if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
    Master.isMouseReleased = false;
    if (friendField.getText().length() > 1) {
     //code to delete friend goes here
     model.removeFriend(friendField.getText());
     
     //filler code for now
     //Master.friends.remove(friendField.getText());
     friendField.setText(""); 
    }
   }
   
   if (!Mouse.isButtonDown(0)){
    Master.isMouseReleased = true;
   }
  }
  
  
  //send chat message button clicked
  userNameLength = model.getPlayer().userName.length();
  if(((xpos>260 && xpos<380) && (ypos>10 && ypos<40)) || (enterHit && chatSelected)) {
   if((Mouse.isButtonDown(0) && Master.isMouseReleased) || (enterHit && chatSelected)) {
    Master.isMouseReleased = false;
    if (chatField.getText().length() > 1) {
     //code to send chat message to server goes here
     newMessage = chatField.getText();
     if ((newMessage.length() + userNameLength) <= 37) {
      model.addChatEntry(newMessage);
      chatError = false;
     }
     else if ((newMessage.length() + (userNameLength*2)) <= 74) {
      //split message in two and send both
      temp = newMessage.substring(0, (37 - userNameLength));
      model.addChatEntry(temp);
      temp = newMessage.substring(37 - userNameLength);
      model.addChatEntry(temp);
      chatError = false;
     }
     
     else if ((newMessage.length() + (userNameLength*3)) <= 110) {
      //split message into 3 and send all
      temp = newMessage.substring(0, (37-userNameLength));
      model.addChatEntry(temp);
      temp = newMessage.substring((37-userNameLength), (74 - (userNameLength*2)));
      model.addChatEntry(temp);
      temp = newMessage.substring((74-(userNameLength*2)));
      model.addChatEntry(temp);
      
     }
     
     else {
      chatError = true;
     }

     chatField.setText(""); 
    }
   }
   
   if (!Mouse.isButtonDown(0)){
    Master.isMouseReleased = true;
   }
  }

  // stud clicked
  if ((xpos > 60 && xpos < 310) && (ypos > 395 && ypos < 645)) {
   if (Mouse.isButtonDown(0)) {
    sbg.enterState(3); // display coming soon screen
   }
  }

  // blackjack clicked
  if ((xpos > 360 && xpos < 610) && (ypos > 395 && ypos < 645)) {
   if (Mouse.isButtonDown(0)) {
    sbg.enterState(9); // black jack options
   }
  }

  // war clicked
  if ((xpos > 660 && xpos < 910) && (ypos > 395 && ypos < 645)) {
   if (Mouse.isButtonDown(0)) {
    sbg.enterState(4); // enter war options
   }
  }

  // Hold'em clicked
  if ((xpos > 960 && xpos < 1210) && (ypos > 395 && ypos < 645)) {
   if (Mouse.isButtonDown(0)) {
    sbg.enterState(7); // display coming soon screen
   }
  }

  // ratscrew clicked
  if ((xpos > 360 && xpos < 610) && (ypos > 95 && ypos < 345)) {
   if (Mouse.isButtonDown(0)) {
    sbg.enterState(3); // display coming soon screen
   }
  }

  // go fish clicked
  if ((xpos > 660 && xpos < 910) && (ypos > 95 && ypos < 345)) {
   if (Mouse.isButtonDown(0)) {
    sbg.enterState(3); // display coming soon screen
   }
  }

  // Account options clicked
  if ((xpos > 1070 && xpos < 1270) && (ypos > 10 && ypos < 50)) {
   if (Mouse.isButtonDown(0)) {
    sbg.enterState(6);
   }
  }

  // log out button clicked
  if ((xpos > 860 && xpos < 1060) && (ypos > 10 && ypos < 50)) {
   if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
    Master.isMouseReleased = false;
    if (model.logout()) {
     sbg.enterState(0); // display login screen
    }
   }

   if (!Mouse.isButtonDown(0)) {
    Master.isMouseReleased = true;
   }
  }
 }

 public int getID() {
  return 1;
 }
}
