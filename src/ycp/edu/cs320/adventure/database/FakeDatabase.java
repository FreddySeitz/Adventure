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

public class FakeDatabase implements IDatabase {
	private List<Account> accounts;
	private List<Map> maps;
	private List<Player> players;
	private List<Creature> creatures;
	private List<Item> items;

	private int accountId;
	private GameEngine engine;

	public FakeDatabase(){
		accountId = -1;
		accounts = new ArrayList<Account>();
		maps = new ArrayList<Map>();
		creatures = new ArrayList<Creature>();
		items = new ArrayList<Item>();
		players = new ArrayList<Player>();
		engine = new GameEngine();
		readInitialData();
	}
	//Make edits to prepare multiple games per account.  Account and game must now be separated.
	//Also try to request password as little as possible for security's sake

	public void setGameEngine(GameEngine engine){
		this.engine = engine;
	}

	public GameEngine getGameEngine(){
		return this.engine;
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

	public boolean loadGame(String username, String password, GameEngine engine){
		accountId = accountExists(username);
		if(accountId == -1){
			return false;	//failed
		}

		else{
			for(Map map : maps){
				if(map.getAccountId() == accountId){
					engine.getGame().setMap(map);
					break;	//stop looking through list
				}
			}

			List<Item> itemlist = new ArrayList<Item>();
			for(Item item : items){
				if(item.getAccountId() == accountId){
					itemlist.add(item);
				}
			}
			engine.getGame().setItems(itemlist);
			//finding all creatures and setting tile location
			List<Creature> list = new ArrayList<Creature>();
			for(Creature creature : creatures){
				if(creature.getAccountId() == accountId){
					creature.setLocation(engine.getGame().getMap().getTile(creature.getLocation().getX(), creature.getLocation().getY()));
					list.add(creature);
				}
			}
			engine.getGame().setCreatures(list);
			//finding player and setting tile location
			for(Player player : players){
				if(player.getAccountId() == accountId){
					player.setLocation(engine.getGame().getMap().getTile(player.getLocation().getX(), player.getLocation().getY()));
					engine.getGame().setPlayer(player);
				}
			}

			//setting items to inventory for creature
			for(Creature creature: engine.getGame().getCreatures()){	//actors
				itemlist = new ArrayList<Item>();
				for(Item creatureitem : creature.getInventory().getInventory()){	//inventory
					for(Item item : engine.getGame().getItems()){	//item list
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
			for(Item playeritem : engine.getGame().getPlayer().getInventory().getInventory()){	//inventory
				for(Item item : engine.getGame().getItems()){	//item list
					if(playeritem.getId() == item.getId()){	//if inventory item == item list
						itemlist.add(engine.createItem(playeritem.getId()));
						break;	//item found, search for next item
					}
				}
			}
			engine.getGame().getPlayer().setEquippedItem(engine.createItem(engine.getGame().getPlayer().getEquippedItem().getId()));
			return true;	//sucessful
		}
	}

	public boolean newAccount(String username, String password, GameEngine engine){
		if(accountExists(username) != -1){
			return false;	//have main handle Account Exists error
		}
		else{
			Account a = new Account(username, password);
			accountId = accounts.get(accounts.size()-1).getId() + 1;	//set id +1 of currently highest id in list
			a.setId(accountId);
			accounts.add(a);
			
			addAccount(username, password, accountId);	//saves account to CSV immediately
			return true;
		}
	}

	//note: unique usernames
	public boolean newGame(String username, GameEngine engine){
		engine.getGame().setItems(engine.defaultItemList(accountId));

		Map map = new Map();
		map.buildDefault(engine);

		List<Creature> actors = new ArrayList<Creature>();
		Player player = new Player();
		engine.getGame().setCreatures(actors);
		engine.getGame().setMap(map);
		engine.getGame().setPlayer(player);
		engine.getGame().setItems(engine.defaultItemList(accountId));

		return true;

	}

	public int accountExists(String username){
		for(Account account : accounts){
			if(account.getUsername().equals(username)){
				return account.getId();
			}
		}
		//return -1 if not found, return account id if found
		return -1;
	}
	
	public boolean login(String username, String password){
		for(Account account : accounts){
			if(account.getUsername().equals(username)){
				if(account.getPassword().equals(password)) {
					return true;
				}
			}
		}
		//return -1 if not found, return account id if found
		return false;
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
