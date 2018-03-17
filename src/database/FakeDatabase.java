package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import game.Account;
import game.Actor;
import game.Item;
import game.Map;
import game.Tile;

public class FakeDatabase {
	private List<Account> accounts;
	private List<Map> maps;
	private List<Item> items;
	private List<Actor> actors;
	
	public FakeDatabase(){
		readInitialData();
	}
	
	public void readInitialData() {
		try {
			items.addAll(InitialData.getInventory());
			accounts.addAll(InitialData.getAccount());
			maps.addAll(InitialData.getMap());
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
	
	//insert queries to gather data from lists
	
	public boolean loadGame(String username, String password){
		//uses private lists in this class to reconstruct all classes.
		int id = accountExists(username, password);
		if(id == -1){
			return false;
		}
		else{
			//TODO: load game from id
			return true;
		}
	}
	
	public boolean newGame(String username, String password){
		//given username and password, checks if account exists yet.
			//if exists, return false (new account failed)
			//else, create new account with specified credentials, create defaults, return true
		if(accountExists(username, password) != -1){
			return false;	//have main handle Account Exists error
		}
		else{
			//TODO: create new game with defaults
			return true;
		}
	}
	
	public int accountExists(String username, String password){
		//TODO: finds account from database.
			//return -1 if not found, return account id if found
		//TODO: don't forget that users can't use '|' in their username or password
		return -1;
	}
	
	private void writeCSV(){		//aka saveGame
		//TODO: compile all data into strings
		//TODO: Obtain data from necessary classes, rather than using the out dated lists in this class
		try {
			PrintWriter accountswriter = new PrintWriter(new File("src/Account.csv"));
			PrintWriter mapswriter = new PrintWriter(new File("src/Map.csv"));
			PrintWriter itemswriter = new PrintWriter(new File("src/Inventory.csv"));
			PrintWriter actorswriter = new PrintWriter(new File("src/Inventory.csv"));
			StringBuilder itembuilder = new StringBuilder();
			StringBuilder accountbuilder = new StringBuilder();
			StringBuilder mapbuilder = new StringBuilder();
			StringBuilder actorbuilder = new StringBuilder();
			
			for(Item item : items){
				itembuilder.append(item.getId());
				itembuilder.append("|");
				itembuilder.append(item.getDescription());
				itembuilder.append("|");
				itembuilder.append(item.getWeight());
				itembuilder.append("|");
				itembuilder.append(item.getDamage());
				itembuilder.append("|");
				itembuilder.append(item.getHealth());
				itembuilder.append("|");
				itembuilder.append(item.getQuestId());
				itembuilder.append("|");
				itembuilder.append(item.getValue());
				itembuilder.append("\n");
			}
			itemswriter.write(itembuilder.toString());
			itemswriter.close();
			
			for(Account account : accounts){
				accountbuilder.append(account.getId());
				accountbuilder.append("|");
				accountbuilder.append(account.getUsername());
				accountbuilder.append("|");
				accountbuilder.append(account.getPassword());
				accountbuilder.append("\n");
			}
			
			accountswriter.write(accountbuilder.toString());
			accountswriter.close();
			
			for(Actor actor : actors){
				
				actorbuilder.append("\n");
			}
			
			mapswriter.write(mapbuilder.toString());
			mapswriter.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
