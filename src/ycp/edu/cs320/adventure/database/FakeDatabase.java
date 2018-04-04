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
			//finding all creatures and setting tile location
			List<Creature> list = new ArrayList<Creature>();
			for(Creature creature : creatures){
				if(creature.getAccountId() == accountId){
					creature.setLocation(game.getMap().getTile(creature.getLocation().getX(), creature.getLocation().getY()));
					list.add(creature);
				}
			}
			game.setCreatures(list);
			//finding player and setting tile location
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
			
			game.setItems(engine.defaultItemList(accountId));
			
			engine.setGame(game);
			Map map = new Map();
			map.buildDefault(engine);
			
			List<Creature> actors = new ArrayList<Creature>();
			Player player = new Player();
			game = new Game(map, player, actors, engine.defaultItemList(accountId), null);
			
			addAccount(username, password, accountId);	//saves account to CSV immediately
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

	//accounts are separate, changing account shouldn't be necessary to saving a game.  Account should already exist
	public void addAccount(String username, String password, int id){
		try {
			PrintWriter accountswriter = new PrintWriter(new File("src/Account.csv"));
			StringBuilder accountbuilder = new StringBuilder();

			for(Account account : accounts){
				accountbuilder.append(account.getId());
				accountbuilder.append("|");
				accountbuilder.append(account.getUsername());
				accountbuilder.append("|");
				accountbuilder.append(account.getPassword());
				accountbuilder.append("\n");
			}
			accountbuilder.append(username);
			accountbuilder.append("|");
			accountbuilder.append(password);
			accountbuilder.append("|");
			accountbuilder.append(id);
			accountbuilder.append("\n");
			
			accountswriter.write(accountbuilder.toString());
			accountswriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void writeCSV(Game game, int id){		//aka saveGame
		try {
			PrintWriter mapswriter = new PrintWriter(new File("src/Map.csv"));
			PrintWriter playerswriter = new PrintWriter(new File("src/Player.csv"));
			PrintWriter creatureswriter = new PrintWriter(new File("src/Creature.csv"));
			PrintWriter itemswriter = new PrintWriter(new File("src/Item.csv"));
			StringBuilder mapbuilder = new StringBuilder();
			StringBuilder playerbuilder = new StringBuilder();
			StringBuilder creaturebuilder = new StringBuilder();
			StringBuilder itembuilder = new StringBuilder();

			Player p = game.getPlayer();	//putting current game data on top of database
			playerbuilder.append(p.getAccountId());
			playerbuilder.append("|");
			for(Item item : p.getInventory().getInventory()){
				playerbuilder.append(item.getId());
				playerbuilder.append(',');
			}
			playerbuilder.append(p.getEquippedItem().getId());
			playerbuilder.append("|");
			playerbuilder.append(p.getHealth());
			playerbuilder.append("|");
			playerbuilder.append(p.getLocation().getX());
			playerbuilder.append("|");
			playerbuilder.append(p.getLocation().getY());
			playerbuilder.append("|");
			playerbuilder.append(p.getBaseDamage());
			playerbuilder.append("|");
			playerbuilder.append(p.getScore());
			playerbuilder.append("\n");
			for(Player player : players){
				if(id == player.getAccountId()){
					//if accountId == accounts ID, skip the data in actors List.  Already added to be on top of written file
				}
				else{
					playerbuilder.append(player.getAccountId());
					playerbuilder.append("|");
					for(Item item : player.getInventory().getInventory()){
						playerbuilder.append(item.getId());
						playerbuilder.append(',');
					}
					playerbuilder.append(player.getEquippedItem().getId());
					playerbuilder.append("|");
					playerbuilder.append(player.getHealth());
					playerbuilder.append("|");
					playerbuilder.append(player.getLocation().getX());
					playerbuilder.append("|");
					playerbuilder.append(player.getLocation().getY());
					playerbuilder.append("|");
					playerbuilder.append(player.getBaseDamage());
					playerbuilder.append("|");
					playerbuilder.append(player.getScore());
					playerbuilder.append("\n");
				}
			}

			playerswriter.write(playerbuilder.toString());
			playerswriter.close();

			for(Creature c : game.getCreatures()){	//putting current game data on top of database
				creaturebuilder.append(c.getAccountId());
				creaturebuilder.append("|");
				for(Item item : c.getInventory().getInventory()){
					creaturebuilder.append(item.getId());
					creaturebuilder.append(',');
				}
				creaturebuilder.append(c.getEquippedItem().getId());
				creaturebuilder.append("|");
				creaturebuilder.append(c.getHealth());
				creaturebuilder.append("|");
				creaturebuilder.append(c.getLocation().getX());
				creaturebuilder.append("|");
				creaturebuilder.append(c.getLocation().getY());
				creaturebuilder.append("|");
				creaturebuilder.append(c.getBaseDamage());
				creaturebuilder.append("|");
				creaturebuilder.append(c.getMovementSpeed());
				creaturebuilder.append("\n");
			}
			for(Creature creature : creatures){
				if(id == creature.getAccountId()){
					//if accountId == accounts ID, skip the data in actors List.  Already added to be on top of written file
				}
				else{
					creaturebuilder.append(creature.getAccountId());
					creaturebuilder.append("|");
					for(Item item : creature.getInventory().getInventory()){
						creaturebuilder.append(item.getId());
						creaturebuilder.append(',');
					}
					creaturebuilder.append(creature.getEquippedItem().getId());
					creaturebuilder.append("|");
					creaturebuilder.append(creature.getHealth());
					creaturebuilder.append("|");
					creaturebuilder.append(creature.getLocation().getX());
					creaturebuilder.append("|");
					creaturebuilder.append(creature.getLocation().getY());
					creaturebuilder.append("|");
					creaturebuilder.append(creature.getBaseDamage());
					creaturebuilder.append("|");
					creaturebuilder.append(creature.getMovementSpeed());
					creaturebuilder.append("\n");
				}
			}

			creatureswriter.write(creaturebuilder.toString());
			creatureswriter.close();

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
				if(id == i.getAccountId()){
					//if accountId == accounts ID, skip the data in actors List.  Already added to be on top of written file
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
				if(id == m.getAccountId()){
					//if accountId == accounts ID, skip the data in maps List.  Already added to be on top of written file
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
