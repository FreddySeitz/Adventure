package ycp.edu.cs320.adventure.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ycp.edu.cs320.adventure.database.FakeDatabase;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

// Operates on the current Game object
public class GameEngine {
	private Game currentGame;
	private DerbyDatabase database;
	
	// Parameterless Constructor
	public GameEngine() {
		currentGame = new Game();
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
		// Changes the player's location
		currentGame.getPlayer().setLocation(newLocation);
		
		// Updates the player's location in the database
		database.updatePlayerX(currentGame.getPlayer().getPlayerId(), newLocation.getX());
		database.updatePlayerY(currentGame.getPlayer().getPlayerId(), newLocation.getY());
		
		// Update the game when the player moves
		update();
	}
	
	// Called when a player chooses to pickup an item
	public void pickupItem(Actor actor, Item item) {
		// Adds the item to the actor's inventory
		actor.getInventory().addToInventory(item);
		
		// Updates the inventory of the actor in the database
		if(actor instanceof Player) {
			database.addToPlayerInventory(((Player) actor).getPlayerId(), item.getItemId());
		}
		else if(actor instanceof Creature) {
			database.addToCreatureInventory(((Creature) actor).getCreatureId(), item.getItemId());
		}
		
		// Removes the item from the inventory of the Tile at the actor's location 
		actor.getLocation().removeItem(item.getItemId());
		
		// Updates the Tile's inventory in the database
		database.removeFromTileInventory(item.getItemId(), item.getInventoryId());
	}
	
	// Called when a player chooses to pickup an item
	public void dropItem(Actor actor, Item item) {
		// Adds the item to the inventory of the tile the actor is located on
		actor.getLocation().addItem(item);
			
		// Removes the item from the actor's inventory
		actor.getInventory().removeFromInventory(item);
		
		// Updates the inventory of the actor in the database
		if(actor instanceof Player) {
			database.removeFromPlayerInventory(((Player) actor).getPlayerId(), item.getItemId());
		}
		else if(actor instanceof Creature) {
			database.removeFromCreatureInventory(((Creature) actor).getCreatureId(), item.getItemId());
		}
		
		// Updates database to reflect inventory change of Tile
		database.addToTileInventory(actor.getLocation().getTileId(), item.getItemId());
	}
	
	// Called when a creature attacks a player
	public void attackPlayer(Creature creature) {
		// Creature attacks the player, changing player health
		creature.attack(currentGame.getPlayer());
		
		// Updates the database to show change in player health
		database.updatePlayerHealth(currentGame.getPlayer().getPlayerId(), currentGame.getPlayer().getHealth());
	}
	
	// Called when a player attacks a creature
	public void attackCreature(Creature creature) {
		// Player attacks the creature, changing creature health
		currentGame.getPlayer().attack(creature);
		
		// Updates the database to show change in creature health
		database.updatePlayerHealth(creature.getCreatureId(), creature.getHealth());
	}
	
	// Randomly moves creatures when the user enters a command
	public void moveCreatures() {
		// Gets an instance of the current Map for later comparisons
		Map currentMap = currentGame.getMap();
		
		// Initialization for necessary temporary variables
		Tile oldLocation = new Tile();
		Tile newLocation = new Tile();
		int newX = 0;
		int newY = 0;
		
		// Loops through all creatures, randomly changing their locations
		for(Creature c : currentGame.getCreatures()){
			oldLocation = c.getLocation();
			
			// Adds -1, 0, or 1 to the creature's X and Y locations
			newX = c.getLocation().getX() + (int)((Math.random() * 2) - 1);
			newY = c.getLocation().getY() + (int)((Math.random() * 2) - 1);
			
			// Checks for boundaries of the map and does not
			// allow a move that would go outside
			if(newX >= 0 && newX < currentMap.getWidth() &&
			   newY >= 0 && newY < currentMap.getHeight()) {
				newLocation = currentMap.getTile(newX, newY);
			}
			else if(newX >= 0 && newX < currentMap.getWidth()) {
				newLocation = currentMap.getTile(newX, c.getLocation().getY());
			}
			else if(newY >= 0 && newY < currentMap.getHeight()) {
				newLocation = currentMap.getTile(c.getLocation().getX(), newY);
			}
			
			// Checks if the newLocation actually changed
			// If so, updates the location of the creature and updates the database
			if(oldLocation != newLocation) {
				c.setLocation(newLocation);
				database.updateCreatureX(c.getCreatureId(), c.getLocation().getX());
				database.updateCreatureY(c.getCreatureId(), c.getLocation().getY());
			}
			
			// If the player is at the same location as the creature
			// the creature decides to attack the player
			if(currentGame.getPlayer().getLocation() == c.getLocation()) {
				attackPlayer(c);
			}
		}
	}
	
	// Stores the current Game object in the database
	public void saveGame(){
		
	}
	
	// Updates the current Game object
	public void update() {
		moveCreatures();
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
