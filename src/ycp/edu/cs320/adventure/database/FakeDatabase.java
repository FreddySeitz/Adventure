package ycp.edu.cs320.adventure.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ycp.edu.cs320.adventure.game.Account;
import ycp.edu.cs320.adventure.game.Actor;
import ycp.edu.cs320.adventure.game.Creature;
import ycp.edu.cs320.adventure.game.Game;
import ycp.edu.cs320.adventure.game.GameEngine;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Player;
import ycp.edu.cs320.adventure.game.Tile;

public class FakeDatabase {
	private List<Account> accounts;
	private List<Map> maps;
	private List<Player> players;
	private List<Creature> creatures;
	private List<Item> items;

	private int accountId;

	public FakeDatabase(){
		accountId = -1;
		readInitialData();
	}

	public void readInitialData() {
		try {
			accounts.addAll(InitialData.getAccount());
			maps.addAll(InitialData.getMap());
			players.addAll(InitialData.getPlayer());
			creatures.addAll(InitialData.getCreature());
			items.addAll(InitialData.getItem());
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}

	//insert queries to gather data from lists

	public boolean loadGame(String username, String password, Game game){
		//uses private lists in this class to reconstruct all classes.
		GameEngine engine = new GameEngine();

		accountId = accountExists(username, password);
		if(accountId == -1){
			return false;	//failed
		}
		else{
			for(Map map : maps){
				if(map.getAccountId() == accountId){
					game.setMap(map);
					break;	//stop looking through list
				}
			}

			List<Item> itemlist = new ArrayList<Item>();
			for(Item item : items){
				if(item.getAccountId() == accountId){
					itemlist.add(item);
				}
			}
			game.setItems(itemlist);
			//finding all creatures
			List<Creature> list = new ArrayList<Creature>();
			for(Creature creature : creatures){
				if(creature.getAccountId() == accountId){
					creature.setLocation(game.getMap().getTile(creature.getLocation().getX(), creature.getLocation().getY()));
					list.add(creature);
				}
			}
			game.setCreatures(list);
			
			for(Player player : players){
				if(player.getAccountId() == accountId){
					player.setLocation(game.getMap().getTile(player.getLocation().getX(), player.getLocation().getY()));
					game.setPlayer(player);
				}
			}

			//setting items to inventory for creature
			for(Creature creature: game.getCreatures()){	//actors
				itemlist = new ArrayList<Item>();
				for(Item creatureitem : creature.getInventory().getInventory()){	//inventory
					for(Item item : game.getItems()){	//item list
						if(creatureitem.getId() == item.getId()){	//if inventory item == item list
							itemlist.add(engine.createItem(creatureitem.getId()));
							break;	//item found, search for next item
						}
					}
				}
				creature.getInventory().setInventory(itemlist);
				creature.setEquippedItem(engine.createItem(creature.getEquippedItem().getId()));	//placing correct item equipped
			}
			
			//setting inventory for player
			itemlist = new ArrayList<Item>();
			for(Item playeritem : game.getPlayer().getInventory().getInventory()){	//inventory
				for(Item item : game.getItems()){	//item list
					if(playeritem.getId() == item.getId()){	//if inventory item == item list
						itemlist.add(engine.createItem(playeritem.getId()));
						break;	//item found, search for next item
					}
				}
			}
			game.getPlayer().setEquippedItem(engine.createItem(game.getPlayer().getEquippedItem().getId()));
			return true;	//sucessful
		}
	}

	public boolean newGame(String username, String password, Game game){
		//given username and password, checks if account exists yet.
		//if exists, return false (new account failed)
		//else, create new account with specified credentials, create defaults, return true
		if(accountExists(username, password) != -1){
			return false;	//have main handle Account Exists error
		}
		else{
			GameEngine engine = new GameEngine();
			Account a = new Account(username, password);
			accountId = accounts.get(accounts.size()-1).getId() + 1;	//set id +1 of currently highest id in list
			a.setId(accountId);
			accounts.add(a);
			Map map = new Map();
			map.buildDefault();
			List<Creature> actors = new ArrayList<Creature>();
			Player player = new Player();
			game = new Game(map, player, actors, engine.defaultItemList(accountId), null);
			return true;
		}
	}

	public int accountExists(String username, String password){
		for(Account account : accounts){
			if(account.getUsername().equals(username) && account.getPassword().equals(password)){
				return account.getId();
			}
		}
		//return -1 if not found, return account id if found
		return -1;
	}

	public void writeCSV(Game game){		//aka saveGame
		//TODO: compile all data into strings
		//TODO: Obtain data from necessary classes, rather than using the out dated lists in this class
		try {
			PrintWriter accountswriter = new PrintWriter(new File("src/Account.csv"));
			PrintWriter mapswriter = new PrintWriter(new File("src/Map.csv"));
			PrintWriter actorswriter = new PrintWriter(new File("src/Actor.csv"));
			PrintWriter itemswriter = new PrintWriter(new File("src/Item.csv"));
			StringBuilder accountbuilder = new StringBuilder();
			StringBuilder mapbuilder = new StringBuilder();
			StringBuilder actorbuilder = new StringBuilder();
			StringBuilder itembuilder = new StringBuilder();

			//TODO: write current data first so it's on top of the list.
			for(Account account : accounts){
				if(false){
					//TODO: if accountId == accounts ID, skip the data in accounts List
				}
				else{
					accountbuilder.append(account.getId());
					accountbuilder.append("|");
					accountbuilder.append(account.getUsername());
					accountbuilder.append("|");
					accountbuilder.append(account.getPassword());
					accountbuilder.append("\n");
				}
			}

			accountswriter.write(accountbuilder.toString());
			accountswriter.close();

			for(Actor a : game.getActors()){	//putting current game data on top of database
				actorbuilder.append(a.getAccountId());
				actorbuilder.append("|");
				for(Item item : a.getInventory().getInventory()){
					actorbuilder.append(item.getId());
					actorbuilder.append(',');
				}
				actorbuilder.append(a.getEquippedItem().getId());
				actorbuilder.append("|");
				actorbuilder.append(a.getHealth());
				actorbuilder.append("|");
				actorbuilder.append(a.getLocation().getX());
				actorbuilder.append("|");
				actorbuilder.append(a.getLocation().getY());
				actorbuilder.append("|");
				actorbuilder.append(a.getBaseDamage());
				actorbuilder.append("\n");
			}
			for(Actor actor : actors){
				if(false){
					//TODO: if accountId == accounts ID, skip the data in actors List.  Already added to be on top of written file
				}
				else{
					actorbuilder.append(actor.getAccountId());
					actorbuilder.append("|");
					for(Item item : actor.getInventory().getInventory()){
						actorbuilder.append(item.getId());
						actorbuilder.append(',');
					}
					actorbuilder.append(actor.getEquippedItem().getId());
					actorbuilder.append("|");
					actorbuilder.append(actor.getHealth());
					actorbuilder.append("|");
					actorbuilder.append(actor.getLocation().getX());
					actorbuilder.append("|");
					actorbuilder.append(actor.getLocation().getY());
					actorbuilder.append("|");
					actorbuilder.append(actor.getBaseDamage());
					actorbuilder.append("\n");
				}
			}

			actorswriter.write(actorbuilder.toString());
			actorswriter.close();

			//putting current game data on top of database
			for(Item i : game.getItems()){
				itembuilder.append(i.getId());
				itembuilder.append("|");
				itembuilder.append(i.getName());
				itembuilder.append("|");
				itembuilder.append(i.getDescription());
				itembuilder.append("|");
				itembuilder.append(i.getAccountId());
				itembuilder.append("|");
				itembuilder.append(i.getId());
				itembuilder.append("|");
				itembuilder.append(i.getWeight());
				itembuilder.append("|");
				itembuilder.append(i.getDamage());
				itembuilder.append("|");
				itembuilder.append(i.getHealth());
				itembuilder.append("|");
				itembuilder.append(i.getQuestId());
				itembuilder.append("|");
				itembuilder.append(i.getValue());
				itembuilder.append("/n");
			}
			for(Item i : items){
				if(false){
					//TODO: if accountId == accounts ID, skip the data in actors List.  Already added to be on top of written file
				}
				else{
					itembuilder.append(i.getId());
					itembuilder.append("|");
					itembuilder.append(i.getName());
					itembuilder.append("|");
					itembuilder.append(i.getDescription());
					itembuilder.append("|");
					itembuilder.append(i.getAccountId());
					itembuilder.append("|");
					itembuilder.append(i.getId());
					itembuilder.append("|");
					itembuilder.append(i.getWeight());
					itembuilder.append("|");
					itembuilder.append(i.getDamage());
					itembuilder.append("|");
					itembuilder.append(i.getHealth());
					itembuilder.append("|");
					itembuilder.append(i.getQuestId());
					itembuilder.append("|");
					itembuilder.append(i.getValue());
					itembuilder.append("/n");
				}
			}

			itemswriter.write(itembuilder.toString());
			itemswriter.close();

			//putting current game data on top of database
			Map map = game.getMap();
			mapbuilder.append(map.getAccountId());
			mapbuilder.append("|");
			mapbuilder.append(map.getHeight());
			mapbuilder.append("|");
			mapbuilder.append(map.getWidth());
			mapbuilder.append("|");
			mapbuilder.append(map.compileTiles());
			mapbuilder.append("\n");

			for(Map m : maps){
				if(false){
					//TODO: if accountId == accounts ID, skip the data in maps List.  Already added to be on top of written file
				}
				else{
					mapbuilder.append(map.getAccountId());
					mapbuilder.append("|");
					mapbuilder.append(map.getHeight());
					mapbuilder.append("|");
					mapbuilder.append(map.getWidth());
					mapbuilder.append("|");
					mapbuilder.append(map.compileTiles());
					mapbuilder.append("\n");
				}
			}

			mapswriter.write(mapbuilder.toString());
			mapswriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
