package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.Account;
import game.Actor;
import game.Item;
import game.Map;
import game.Tile;

public class InitialData {
	//unpacks everything from the database into lists
	public static List<Map> getMap() throws IOException {
		List<Map> mapList = new ArrayList<Map>();
		ReadCSV readMap = new ReadCSV("Map.csv");
		try {
			while (true) {
				List<String> tuple = readMap.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Map map = new Map();
				map.setAccountId(Integer.parseInt(i.next()));		//account id
				map.setHeight(Integer.parseInt(i.next()));			//map height
				map.setWidth(Integer.parseInt(i.next()));			//map width
				map.decompileTiles(i.next()); 						//creates all tiles and retrieves their data
				mapList.add(map);
			}
			return mapList;
		} finally {
			readMap.close();
		}
	}


	public static List<Account> getAccount() throws IOException {
		List<Account> accountList = new ArrayList<Account>();
		ReadCSV readAccount = new ReadCSV("Account.csv");
		try {
			while (true) {
				List<String> tuple = readAccount.next();
				if (tuple == null) {
					break;
				}
				
				Iterator<String> i = tuple.iterator();
				Account account = new Account("","");
				account.setId(Integer.parseInt(i.next()));
				account.setUsername(i.next());
				account.setPassword(i.next());
				accountList.add(account);
			}
			return accountList;
		} finally {
			readAccount.close();
		}
	}
	
	public static List<Item> getInventory() throws IOException {
		List<Item> itemList = new ArrayList<Item>();
		ReadCSV readAccount = new ReadCSV("Inventory.csv");
		try {
			while (true) {
				List<String> tuple = readAccount.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Item item = new Item("",0,0,0,0,0);
				item.setId(Integer.parseInt(i.next()));
				item.setDescription(i.next());
				item.setWeight(Integer.parseInt(i.next()));
				item.setDamage(Integer.parseInt(i.next()));
				item.setHealth(Integer.parseInt(i.next()));
				item.setQuestId(Integer.parseInt(i.next()));
				item.setValue(Integer.parseInt(i.next()));
				itemList.add(item);
			}
			return itemList;
		} finally {
			readAccount.close();
		}
	}
	
	public static List<Actor> getActor() throws IOException {
		List<Actor> actorList = new ArrayList<Actor>();
		ReadCSV readAccount = new ReadCSV("Inventory.csv");
		try {
			while (true) {
				List<String> tuple = readAccount.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Actor actor = new Actor();
				actorList.add(actor);
				
				String inv = i.next();
				StringBuilder item = new StringBuilder();
				for(int k = 0; k < inv.length(); k++){
					if(inv.charAt(k) == '|'){
						//TODO: get item from dictionary
						k++;
					}
					else{
						item.append(inv.charAt(k));
					}
				}
			}
			return actorList;
		} finally {
			readAccount.close();
		}
	}
}
