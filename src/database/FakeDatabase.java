package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import game.Account;
import game.Item;
import game.Map;
import game.Tile;

public class FakeDatabase {
	private List<Account> accounts;
	private List<Map> maps;
	private List<Item> items;
	
	public FakeDatabase(){
		readInitialData();
	}
	
	public void readInitialData() {
		try {
			items.addAll(InitialData.getItem());
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
			PrintWriter itemswriter = new PrintWriter(new File("src/Item.csv"));
			StringBuilder itembuilder = new StringBuilder();
			StringBuilder accountbuilder = new StringBuilder();
			
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
//				accountbuilder.append("|");
//				accountbuilder.append(account);	//compile characters into a single string
//				accountbuilder.append("|");
//				accountbuilder.append(account);	//compile games list into single string
				accountbuilder.append("\n");
			}
			
			accountswriter.write(accountswriter.toString());
			accountswriter.close();
			
			for(Map map : maps){
				itembuilder.append(map.getAccountId());
				itembuilder.append("|");
				itembuilder.append(map.getHeight());
				itembuilder.append("|");
				itembuilder.append(map.getWidth());
				itembuilder.append("|");
				itembuilder.append(map.compileTiles());
				itembuilder.append("|");
				itembuilder.append(map.getAccountId());
				itembuilder.append("\n");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
