package ycp.edu.cs320.adventure.game;

// Actors are any object that moves around the Map
public class Actor {
	private int gameId;
	private Inventory inventory;
	private Item equippedItem;
	private int health;
	private Tile location;
	private int baseDamage;
	
	// Parameterless Constructor
	public Actor(){
		inventory = new Inventory();
		equippedItem = new Item();
		location = new Tile();
	}
	
	// Constructor
	public Actor(int accountId, Inventory inventory, Item equippedItem, int health, Tile location, int baseDamage){
		this.gameId = accountId;
		this.inventory = inventory;
		this.equippedItem = equippedItem;
		this.health = health;
		this.location = location;
		this.baseDamage = baseDamage;
	}
	
	// Sets the Account ID
	public void setGameId(int id){
		gameId = id;
	}
	
	// Gets the Account ID
	public int getGameId(){
		return gameId;
	}
	
	// Sets inventory
	public void setInventory(Inventory items) {
		this.inventory = items;
	}
	
	// Gets inventory
	public Inventory getInventory(){
		return inventory;
	}
	
	// Sets equipped item
	public void setEquippedItem(Item item) {
			this.equippedItem = item;
	}
		
	// Gets equipped item
	public Item getEquippedItem(){
		return equippedItem;
	}
	
	// Sets a new health
	public void setHealth(int health) {
		this.health = health;
	}
			
	// Returns health of Actor
	public int getHealth() {
		return health;
	}
	
	// Sets a new location
	public void setLocation(Tile location) {
		this.location = location;
	}
				
	// Returns location of Actor
	public Tile getLocation() {
		return location;
	}
	
	// Sets a new base damage
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}
				
	// Returns base damage of Actor
	public int getBaseDamage() {
		return baseDamage;
	}
	
	public void attack(Actor target) {
		if(this.equippedItem != null) {
			target.hurt(equippedItem.getDamage());
		}
		else{
			target.hurt(baseDamage);
		}
	}
	
	public void hurt(int damageTaken) {
		this.health = this.health - damageTaken;
	}
	
	public void move(Tile newLocation) {
		this.setLocation(newLocation);
	}
}
