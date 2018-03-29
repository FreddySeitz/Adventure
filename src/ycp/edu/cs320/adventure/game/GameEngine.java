package ycp.edu.cs320.adventure.game;

import ycp.edu.cs320.adventure.database.FakeDatabase;

// Operates on the current Game object
public class GameEngine {
	private Game currentGame;
	
	public GameEngine() {
		
	}
	
	// Ends the game, incorporating checks for winning
	public void endGame() {
		
	}
	
	// Loads a game
	public void loadGame(Game game) {
		currentGame = game;
	}
	
	// Prompts the user for a new command
	public void prompt(String command) {
		
	}
	
	// Stores the current Game object in the database
	public void saveGame(){
		new FakeDatabase().writeCSV(currentGame);
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
}
