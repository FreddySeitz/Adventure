package ycp.edu.cs320.adventure.game;

import java.util.List;

// Actors are any object that moves around the Map
public class Inventory {
	private List<Item> inventory;
	
	public Inventory(List<Item> items){
		inventory = items;
	}
	
	// Sets inventory
	public void setInventory(List<Item> items) {
		this.inventory = items;
	}
	
	// Gets inventory
	public List<Item> getInventory(){
		return inventory;
	}
	
	// Adds a new item to inventory
	public void addToInventory(Item item) {
		inventory.add(item);
	}
	
	// Adds new items to inventory
	public void addMultipleToInventory(List<Item> items) {
		inventory.addAll(items);
	}
	
	// Removes an item from inventory
	public void removeFromInventory(Item item) {
		if(inventory.contains(item)) {
			inventory.remove(inventory.indexOf(item));
		}
	}
}
