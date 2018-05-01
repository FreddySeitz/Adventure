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
	
	// Checks end game conditions
	public boolean checkEndGame() {
		// Checks if player's health hits zero
		if(currentGame.getPlayer().getHealth() <= 0) {
			return true;
		}
		
		// Checks if the player's score is high enough and that they are on
		// the exit tile
		if(currentGame.getPlayer().getScore() == 500 &&
				currentGame.getPlayer().getLocation().getType() == 3) {
			return true;
		}
		
		return false;
	}
	
	// Ends the game
	public void endGame() {
		
	}
	
	// Creates the default game
	public int createGame(int accountId) {
		// Create game and record its gameId
		int gameId = database.createGame(accountId);
		currentGame.setGameId(gameId);
		// Placeholders that will be put in the new game
		Map map = new Map();
		String gameLog = "You decided to set out on an Adventure!";
		List<Creature> creatures = new ArrayList<Creature>();
		List<Item> items = new ArrayList<Item>();
		Player player = new Player();
		
		// Build map
		map.buildDefault();
		
		// Add all Tiles in Map to database
		Tile tile = new Tile();
		int id;
		for(int i=0; i<20; i++) {
			for(int j=0; j<20; j++) {
				tile = map.getTile(i, j);
				id = database.createTile(gameId, tile.getType(), tile.getDescription(), tile.getDamage(), tile.getX(), tile.getY());
				map.getTile(i, j).setTileId(id);
			}
		}
		
		// Add map to database
		database.createMap(gameId, map.getHeight(), map.getWidth());
		
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
			newX = (int)(Math.random() * map.getWidth()-1);
			newY = (int)(Math.random() * map.getHeight()-1);
			creature.setLocation(map.getTile(newX, newY));
			
			// Update database to contain creature
			creatureId = database.createCreature(gameId, 0, creature.getHealth(), newX, newY, i, i);
			
			// Set creatureId given by database
			creature.setCreatureId(creatureId);
			
			// Add the Creature to the existing list of creatures
			creatures.add(creature);
		}
		
		// Set initial items for game
		items = createRandomItems(3);
		
		player.setBaseDamage(5);
		player.setEquippedItem(null);
		player.setGameId(gameId);
		player.setHealth(100);
		player.setInventory(null);
		player.setLocation(map.getTile(0, 0));
		player.setScore(0);
		
		player.setPlayerId(database.createPlayer(gameId, 0, player.getHealth(), player.getLocation().getX(), player.getLocation().getY(), player.getBaseDamage(), player.getScore()));
		
		currentGame.setMap(map);
		currentGame.setGameLog(gameLog);
		currentGame.setCreatures(creatures);
		currentGame.setItems(items);
		currentGame.setPlayer(player);
		
		return gameId;
	}
	
	// Calls database methods to initialize a game
	public void loadGame(int gameId){
		// Loads all creatures for game
		currentGame.setMap(database.getMap(gameId));
		currentGame.setGameLog(database.getGameLog(gameId));
		currentGame.setCreatures(database.getAllCreatures(gameId));
		currentGame.setItems(database.getAllItems(gameId));
		currentGame.setPlayer(database.getPlayer(gameId));
		currentGame.setGameId(gameId);
	}
	
	// Moves the player based on command
	public void movePlayer(int X, int Y) {
		// Changes the player's location
		currentGame.getPlayer().setLocation(currentGame.getMap().getTile(X, Y));
		
		// Updates the player's location in the database
		database.updatePlayerX(currentGame.getPlayer().getPlayerId(), X);
		database.updatePlayerY(currentGame.getPlayer().getPlayerId(), Y);
		
		// Need to get player from database after Tile location is updated
		currentGame.setPlayer(database.getPlayer(currentGame.getGameId()));
		
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
	
	// Called when the player uses a healing item
	public void healPlayer() {
		// Uses equipped item damage which will be negative for healing items
		currentGame.getPlayer().heal(-currentGame.getPlayer().getEquippedItem().getDamage());
		
		// Updates database to reflect change in Player health
		database.updatePlayerHealth(currentGame.getPlayer().getPlayerId(), currentGame.getPlayer().getHealth());
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
	
	public String viewMap(){	//may also be written as a list of text, per row
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < currentGame.getMap().getHeight(); i++){
			for(int k = 0; k < currentGame.getMap().getWidth(); k++){
				switch(currentGame.getMap().getTile(i, k).getType()) {
				case 0:		//unpassable space
					builder.append('-');
				case 1:		//room
					builder.append('o');
				case 2:		//trap
					builder.append('x');
				case 3:		//exit
					builder.append('E');
				}
			}
			builder.append(' ');	//new line
		}
		return builder.toString();
	}
	
	// Updates the current Game object
	public void update() {
		// Update creature location
		moveCreatures();
		
		// Check for end game conditions
		if(checkEndGame()) {
			// Ask user if they would like to end game
			// Call endGame if yes
		}
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
	
	public List<Item> createDefaultItems(){
		List<Item> items = new ArrayList<Item>();
		Item item = new Item();
		Map map = database.getMap(currentGame.getGameId());
		Tile tile = new Tile();
		int itemId;
		Random rand = new Random();
		
		// Treasure item
		int value = rand.nextInt(200) + 400;
		item.setName("Treasure");
		item.setDescription("ohhhh shiny!");
		item.setGameId(currentGame.getGameId());
		item.setWeight(0);
		item.setDamage(1);
		item.setHealth(0);
		item.setQuestId(0);
		item.setValue(value);
		itemId = database.createItem(currentGame.getGameId(), item.getName(), item.getDescription(), item.getWeight(), item.getDamage(), item.getHealth(), item.getQuestId(), item.getValue());
		item.setItemId(itemId);
		items.add(item);
		
		tile = database.getTile(currentGame.getGameId(), 2, 3);
		tile.addItem(item);
		map.setTile(tile.getX(), tile.getY(), tile);
		
		// Updates Tile inventory in the database
		database.addToTileInventory(tile.getTileId(), item.getItemId());
		
		// Sword item
		itemId = database.createItem(currentGame.getGameId(), "Sword", "Sharpened on lost souls", 1, 10, 5, 0, 1);
		items.add(new Item("Sword", "Sharpened on lost souls", currentGame.getGameId(), itemId, 1, 10, 5, 0, 1));
		
		// Pebble item
		itemId = database.createItem(currentGame.getGameId(), "Pebble", "Hard and small", 1, 5, 0, 0, 0);
		items.add(new Item("Pebble", "Hard and small", currentGame.getGameId(), itemId, 1, 5, 0, 0, 0));
		
		return items;
	}
	
	public List<Item> createRandomItems(int numItems){
		List<Item> items = new ArrayList<Item>();
		Item item;
		int newX;
		int newY;
		Map map = database.getMap(currentGame.getGameId());
		int itemId;
		Tile tile;
		
		// Add items to list of items
		for(int i=1; i<numItems; i++) {
			item = new Item();
			item.setDamage(i);
			item.setDescription("Item " + Integer.toString(i));
			item.setGameId(currentGame.getGameId());
			item.setHealth(i);
			item.setName("Item " + Integer.toString(i));
			item.setQuestId(i);
			item.setValue(i);
			item.setWeight(i);
			
			
			// Generates random X and Y locations for Item's starting Tile
			newX = (int)(Math.random() * map.getWidth()-1);
			newY = (int)(Math.random() * map.getHeight()-1);
			
			// Adds the item to the randomly selected Tile inventory
			tile = database.getTile(currentGame.getGameId(), newX, newY);
			tile.addItem(item);
			map.setTile(tile.getX(), tile.getY(), tile);
			
			
			// Update database to contain item
			itemId = database.createItem(currentGame.getGameId(), item.getName(), item.getDescription(), item.getWeight(), item.getDamage(), item.getHealth(), item.getQuestId(), item.getValue());
			
			// Set itemId given by database
			item.setItemId(itemId);
			
			// Add the Item to the existing list of items
			items.add(item);
			
			// Updates Tile inventory in the database
			database.addToTileInventory(tile.getTileId(), item.getItemId());
		}
		
		return items;
	}
	
	//the list of items that can exist in an unedited new game
	public List<Item> defaultItemList(int gameId){
		List<Item> itemList = new ArrayList<Item>();
		int itemID = 1;
		//ensure treasure is Itemid 1
		Random rand = new Random();
		Item treasure = new Item("Treasure", "ohhhh shiny!", gameId,itemID,0,1,0,0,rand.nextInt(200) + 400); //random value between 400-600
		itemID++;
		Item sword = new Item("Sword", "Sharpened on lost souls", gameId, itemID, 1, 10, 5, 0, 1);
		itemID++;
		Item pebble = new Item("Pebble", "Hard and small", gameId, itemID, 1, 10, 5, 0, 0);
		itemID++;
		
		itemList.add(treasure);
		itemList.add(sword);
		itemList.add(pebble);
		
		return itemList;
	}
}
