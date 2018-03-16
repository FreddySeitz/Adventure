package game;

import java.util.List;

// Actors are any object that moves around the Map
public class Actor {
	private List<Item> inventory;
	private Item equippedItem;
	private int health;
	private Tile location;
	
	public Actor(){
		
	}
	
	// Sets inventory
	public void setInventory(List<Item> i) {
		this.inventory = i;
	}
	
	// Gets inventory
	public List<Item> getInventory(){
		return inventory;
	}
	
	// Sets equipped item
	public void setEquippedItem(Item i) {
			this.equippedItem = i;
	}
		
	// Gets equipped item
	public Item getEquippedItem(){
		return equippedItem;
	}
	
	// Sets a new health
	public void setHealth(int h) {
		this.health = h;
	}
			
	// Returns health of Actor
	public int getHealth() {
		return health;
	}
	
	// Sets a new location
	public void setLocation(Tile l) {
		this.location = l;
	}
				
	// Returns location of Actor
	public Tile getLocation() {
		return location;
	}
	
	public void attack() {
		throw new UnsupportedOperationException();
	}
	
	public void hurt() {
		throw new UnsupportedOperationException();
	}
	
	public void move(Tile newLocation) {
		this.setLocation(newLocation);
	}
}
