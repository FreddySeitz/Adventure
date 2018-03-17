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
	
	private void writeCSV(){
		try {
			PrintWriter accountswriter = new PrintWriter(new File("src/Account.csv"));
			PrintWriter mapswriter = new PrintWriter(new File("src/Map.csv"));
			PrintWriter itemswriter = new PrintWriter(new File("src/Inventory.csv"));
			PrintWriter actorswriter = new PrintWriter(new File("src/Inventory.csv"));
			StringBuilder itembuilder = new StringBuilder();
			StringBuilder accountbuilder = new StringBuilder();
			StringBuilder mapbuilder = new StringBuilder();
			
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
				mapbuilder.append(actor.getAccountId());
				mapbuilder.append("|");
				mapbuilder.append(actor.getHeight());
				mapbuilder.append("|");
				mapbuilder.append(actor.getWidth());
				mapbuilder.append("|");
				mapbuilder.append(actor.compileTiles());
				mapbuilder.append("\n");
			}
			
			mapswriter.write(mapbuilder.toString());
			mapswriter.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
