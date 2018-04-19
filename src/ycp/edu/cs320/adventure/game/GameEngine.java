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
	
	// Constructor
	public GameEngine() {
		currentGame = new Game();
		database = new DerbyDatabase();
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
	
	// Creates the default game
	public void createGame(int accountId) {
		// Create game and record its gameId
		int gameId = database.createGame(accountId);
		
		// Placeholders that will be put in the new game
		Map map = new Map();
		String gameLog = "You decided to set out on an Adventure!";
		List<Creature> creatures = new ArrayList<Creature>();
		List<Item> items = new ArrayList<Item>();
		Player player = new Player();
		
		// Build map
		map.buildDefault();
		
		// Temporary creature object
		Creature creature;
		int creatureId;
		int newX;
		int newY;
				
		// Add creatures to list of creatures
		for(int i=1; i<3; i++) {
			creature = new Creature(i);
			creature.setBaseDamage(i);
			creature.setGameId(gameId);
			creature.setHealth(100);
			creature.setInventory(new Inventory());
			creature.setMovementSpeed(i);
			
			// Generates random X and Y locations for Creature's starting Tile
			newX = (int)(Math.random() * map.getWidth());
			newY = (int)(Math.random() * map.getHeight());
			creature.setLocation(map.getTile(newX, newY));
			
			// Update database to contain creature
			creatureId = database.createCreature(gameId, 0, creature.getHealth(), newX, newY, i, i);
			
			// Set creatureId given by database
			creature.setCreatureId(creatureId);
			
			// Add the Creature to the existing list of creatures
			creatures.add(creature);
		}
		
		// Temporary item object
		Item item;
		int itemId;
				
		// Add items to list of items
		for(int i=1; i<3; i++) {
			item = new Item();
			item.setDamage(i);
			item.setDescription("Item " + Integer.toString(i));
			item.setGameId(gameId);
			item.setHealth(i);
			item.setName("Item " + Integer.toString(i));
			item.setQuestId(i);
			item.setValue(i);
			item.setWeight(i);
			
			
			// Generates random X and Y locations for Item's starting Tile
			newX = (int)(Math.random() * map.getWidth());
			newY = (int)(Math.random() * map.getHeight());
			
			map.getTile(newX, newY).addItem(item);
			
			// Update database to contain item
			itemId = database.createItem(gameId, item.getName(), item.getDescription(), item.getWeight(), item.getDamage(), item.getHealth(), item.getQuestId(), item.getValue());
			
			// Set itemId given by database
			item.setItemId(itemId);
			
			// Add the Item to the existing list of items
			items.add(item);
		}
		
		player.setBaseDamage(5);
		player.setEquippedItem(null);
		player.setGameId(gameId);
		player.setHealth(100);
		player.setInventory(null);
		player.setLocation(map.getTile(0, 0));
		player.setScore(0);
		
		database.createPlayer(gameId, 0, player.getHealth(), player.getLocation().getX(), player.getLocation().getY(), player.getBaseDamage(), player.getScore());
		
		currentGame.setMap(map);
		currentGame.setGameLog(gameLog);
		currentGame.setCreatures(creatures);
		currentGame.setItems(items);
		currentGame.setPlayer(player);
		currentGame.setGameId(gameId);
		currentGame.setPlayer(database.getPlayer(gameId));
	}
	
	// Calls database methods to initialize a game
	public void loadGame(int gameId){
		// Loads all creatures for game
		currentGame.setCreatures(database.getAllCreatures(gameId));
	}
	
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
}
