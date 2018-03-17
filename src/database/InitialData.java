package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.Account;
import game.Item;
import game.Map;
import game.Tile;

public class InitialData {

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
				map.setAccountId(Integer.parseInt(i.next()));
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
				//assemble list of characters
				//assemble list of games
				accountList.add(account);
			}
			return accountList;
		} finally {
			readAccount.close();
		}
	}
	
	public static List<Item> getItem() throws IOException {
		List<Item> itemList = new ArrayList<Item>();
		ReadCSV readAccount = new ReadCSV("Item.csv");
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
}
