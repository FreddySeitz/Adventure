package game;

import java.util.List;

// Actors are any object that moves around the Map
public class Actor {
	private List<Item> inventory;
	private Item equippedItem;
	private int health;
	private Tile location;
	private int baseDamage;
	
	public Actor(){
		
	}
	
	// Sets inventory
	public void setInventory(List<Item> items) {
		this.inventory = items;
	}
	
	// Gets inventory
	public List<Item> getInventory(){
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
			target.hurt(equippedItem.damage);
		}
		else{
			target.hurt(baseDamage);
		}*/
	}
	
	public void hurt(int damage) {
		this.health = this.health - damage;
	}
	
	public void move(Tile newLocation) {
		this.setLocation(newLocation);
	}
}
