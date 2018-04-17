package ycp.edu.cs320.adventure.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ycp.edu.cs320.adventure.database.FakeDatabase;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

// Operates on the current Game object
public class GameEngine {
	private Game currentGame;
	private String gameLog;
	private DerbyDatabase database;
	
	// Parameterless Constructor
	public GameEngine() {
		currentGame = new Game();
		gameLog = "You decided to set out on an Adventure!";
		database = new DerbyDatabase();
	}
	
	// Constructor
	public GameEngine(Game game, DerbyDatabase database) {
		currentGame = game;
		this.database = database;
	}
	
	// Sets currentGame object
	public void setGame(Game g){
		currentGame = g;
	}
	
	// Gets currentGame object
	public Game getGame(){
		return currentGame;
	}
	
	// Sets gameLog object
	public void setGameLog(String gameLog){
		this.gameLog = gameLog;
	}
	
	// Gets gameLog object
	public String getGameLog(){
		return gameLog;
	}
	
	// Sets Database object
	public void setDatabase(DerbyDatabase database){
		this.database = database;
	}
	
	// Gets Database object
	public DerbyDatabase getDatabase(){
		return database;
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
	
	// Moves the player based on command
	public void movePlayer(Tile newLocation) {
		currentGame.getPlayer().setLocation(newLocation);
		//database.updatePlayerLocation(currentGame.getPlayer().getGameId(), newLocation);
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
			if(i.getItemId() == id){
				item.setGameId(i.getGameId());
				item.setDamage(i.getDamage());
				item.setDescription(i.getDescription());
				item.setHealth(i.getHealth());
				item.setItemId(i.getItemId());
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
