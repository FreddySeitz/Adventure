package ycp.edu.cs320.adventure.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ycp.edu.cs320.adventure.game.Account;
import ycp.edu.cs320.adventure.game.Actor;
import ycp.edu.cs320.adventure.game.Game;
import ycp.edu.cs320.adventure.game.GameEngine;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Player;
import ycp.edu.cs320.adventure.game.Tile;

public class FakeDatabase {
	private List<Account> accounts;
	private List<Map> maps;
	private List<Actor> actors;
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
			actors.addAll(InitialData.getActor());
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

			List<Actor> list = new ArrayList<Actor>();
			for(Actor actor : actors){
				if(actor.getAccountId() == accountId){
					actor.setLocation(game.getMap().getTile(actor.getLocation().getX(), actor.getLocation().getY()));
					list.add(actor);
				}
			}
			game.setActors(list);

			//setting items to inventory
			for(Actor actor: game.getActors()){	//actors
				itemlist = new ArrayList<Item>();
				for(Item actoritem: actor.getInventory().getInventory()){	//inventory
					for(Item item : game.getItems()){	//item list
						if(actoritem.getId() == item.getId()){	//if inventory item == item list
							itemlist.add(engine.createItem(actoritem.getId()));
							break;
						}
					}
				}
				actor.getInventory().setInventory(itemlist);
				actor.setEquippedItem(engine.createItem(actor.getEquippedItem().getId()));	//placing correct item equipped
			}
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
			Map map = new Map();
			map.buildDefault();
			List<Actor> actors = new ArrayList<Actor>();
			Player player = new Player();
			actors.add(player);
			game = new Game(map, actors, null, null);
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

			for(Item i : game.getItems()){	//putting current game data on top of database
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
