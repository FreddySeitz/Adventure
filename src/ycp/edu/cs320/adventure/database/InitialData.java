package ycp.edu.cs320.adventure.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ycp.edu.cs320.adventure.game.Account;
import ycp.edu.cs320.adventure.game.Actor;
import ycp.edu.cs320.adventure.game.Creature;
import ycp.edu.cs320.adventure.game.Game;
import ycp.edu.cs320.adventure.game.Inventory;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Player;
import ycp.edu.cs320.adventure.game.Tile;

public class InitialData {
	//unpacks everything from the database into lists
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
	
	public static List<Player> getPlayer() throws IOException {
		List<Player> playerList = new ArrayList<Player>();
		ReadCSV readPlayer = new ReadCSV("Player.csv");
		try {
			while (true) {
				List<String> tuple = readPlayer.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Player player = new Player();
				player.setAccountId(Integer.parseInt(i.next()));
				
				String inv = i.next();
				StringBuilder item = new StringBuilder();	//reads a single item ID
				List<Item> inventoryList = new ArrayList<Item>();
				//inventory
				for(int k = 0; k < inv.length(); k++){
					if(inv.charAt(k) == ','){
						//just add item ID, when game gets loaded, fill in rest of item data for the item set for that account
						inventoryList.add(new Item("", "", Integer.parseInt(item.toString()),0,0,0,0,0,0));
						k++;
						item.delete(0, item.length());
					}
					else{
						item.append(inv.charAt(k));
					}
				}
				Inventory inventory = new Inventory(inventoryList);
				player.setInventory(inventory);
				player.setEquippedItem(new Item("","",Integer.parseInt(i.next()),0,0,0,0,0,0));
				player.setHealth(Integer.parseInt(i.next()));
				player.setLocation(new Tile());		//TODO: set to the real tile later, after game has been matched with id
				player.getLocation().setX(Integer.parseInt(i.next()));
				player.getLocation().setY(Integer.parseInt(i.next()));
				player.setBaseDamage(Integer.parseInt(i.next()));
				player.setScore(Integer.parseInt(i.next()));
				
				playerList.add(player);
			}
			return playerList;
		} finally {
			readPlayer.close();
		}
	}
	
	public static List<Creature> getCreature() throws IOException {
		List<Creature> creatureList = new ArrayList<Creature>();
		ReadCSV readCreature = new ReadCSV("Creature.csv");
		try {
			while (true) {
				List<String> tuple = readCreature.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Creature creature = new Creature();
				creature.setAccountId(Integer.parseInt(i.next()));
				
				String inv = i.next();
				StringBuilder item = new StringBuilder();	//reads a single item ID
				List<Item> inventoryList = new ArrayList<Item>();
				//inventory
				for(int k = 0; k < inv.length(); k++){
					if(inv.charAt(k) == ','){
						//just add item ID, when game gets loaded, fill in rest of item data for the item set for that account
						inventoryList.add(new Item("", "", Integer.parseInt(item.toString()),0,0,0,0,0,0));
						k++;
						item.delete(0, item.length());
					}
					else{
						item.append(inv.charAt(k));
					}
				}
				Inventory inventory = new Inventory(inventoryList);
				creature.setInventory(inventory);
				creature.setEquippedItem(new Item("","",Integer.parseInt(i.next()),0,0,0,0,0,0));
				creature.setHealth(Integer.parseInt(i.next()));
				creature.setLocation(new Tile());		//TODO: set to the real tile later, after game has been matched with id
				creature.getLocation().setX(Integer.parseInt(i.next()));
				creature.getLocation().setY(Integer.parseInt(i.next()));
				creature.setBaseDamage(Integer.parseInt(i.next()));
				creature.setMovementSpeed(Integer.parseInt(i.next()));
				
				creatureList.add(creature);
			}
			return creatureList;
		} finally {
			readCreature.close();
		}
	}
	
	public static List<Item> getItem() throws IOException {
		List<Item> itemList = new ArrayList<Item>();
		ReadCSV readItem = new ReadCSV("Item.csv");
		try {
			while (true) {
				List<String> tuple = readItem.next();
				if (tuple == null) {
					break;
				}
				
				Iterator<String> i = tuple.iterator();
				Item item = new Item("","",0,0,0,0,0,0,0);
				item.setId(Integer.parseInt(i.next()));
				item.setName(i.next());
				item.setDescription(i.next());
				item.setAccountId(Integer.parseInt(i.next()));
				item.setId(Integer.parseInt(i.next()));
				item.setWeight(Integer.parseInt(i.next()));
				item.setDamage(Integer.parseInt(i.next()));
				item.setHealth(Integer.parseInt(i.next()));
				item.setQuestId(Integer.parseInt(i.next()));
				item.setValue(Integer.parseInt(i.next()));
				itemList.add(item);
			}
			return itemList;
		} finally {
			readItem.close();
		}
	}
}