package ycp.edu.cs320.adventure.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ycp.edu.cs320.adventure.database.FakeDatabase;

// Operates on the current Game object
public class GameEngine {
	private Game currentGame;
	
	public GameEngine() {
		
	}
	
	public void setGame(Game g){
		currentGame = g;
	}
	
	public Game getGame(){
		return currentGame;
	}
	
	// Ends the game, incorporating checks for winning
	public void endGame() {
		
	}
	
	// Loads a game
	//database calls loadGame (database needs gameEngine)
//	public void loadGame(Game game) {
//		currentGame = game;
//	}
	
	//gameEngine calls database (gameEngine needs database)
//	public void loadGame(){
//		//from where ever the fakedatabase instance is
//		FakeDatabase.loadGame(username, password, currentGame);
//	}
	
	// Prompts the user for a new command
	public void prompt(String command) {
		
	}
	
	// Stores the current Game object in the database
	public void saveGame(){
		
	}
	
	// Updates the current Game object
	public void update() {
		
	}
	
	// Creates a new item given an item ID
	public Item createItem(int id){
		Item item = new Item("", "", 0,0,0,0,0,0,0);
		for(Item i : currentGame.getItems()){
			if(i.getId() == id){
				item.setAccountId(i.getAccountId());
				item.setDamage(i.getDamage());
				item.setDescription(i.getDescription());
				item.setHealth(i.getHealth());
				item.setId(i.getId());
				item.setName(i.getName());
				item.setQuestId(i.getQuestId());
				item.setValue(i.getValue());
				item.setWeight(i.getWeight());
				break;
			}
		}
		return item;
	}
	
	//the list of items that can exist in an unedited new game
	public List<Item> defaultItemList(int accountId){
		List<Item> itemList = new ArrayList<Item>();
		int itemID = 1;
		//ensure treasure is Itemid 1
		Random rand = new Random();
		Item treasure = new Item("Treasure", "ohhhh shiny!", accountId,itemID,0,1,0,0,rand.nextInt(200) + 400); //random value between 400-600
		itemID++;
		Item sword = new Item("Sword", "Sharpened on lost souls", accountId, itemID, 1, 10, 5, 0, 1);
		itemID++;
		Item pebble = new Item("Pebble", "Hard and small", accountId, itemID, 1, 10, 5, 0, 0);
		itemID++;
		
		itemList.add(treasure);
		itemList.add(sword);
		itemList.add(pebble);
		
		return itemList;
	}

	public static void createNewGame(String username, String password) {
			
	}
}
