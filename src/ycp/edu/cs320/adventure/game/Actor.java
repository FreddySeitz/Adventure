package ycp.edu.cs320.adventure.game;

// Actors are any object that moves around the Map
public class Actor {
	private int accountId;
	private Inventory inventory;
	private Item equippedItem;
	private int health;
	private Tile location;
	private int baseDamage;
	
	public Actor(){
		
	}
	
	public void setAccountId(int id){
		accountId = id;
	}
	
	public int getAccountId(){
		return accountId;
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
		/*if(this.equippedItem instanceof Weapon) {
			target.hurt(equippedItem.getDamage());
		}
		else{
			target.hurt(baseDamage);
		}*/
	}
	
	public void hurt(int damageTaken) {
		this.health = this.health - damageTaken;
	}
	
	public void move(Tile newLocation) {
		this.setLocation(newLocation);
	}
}
