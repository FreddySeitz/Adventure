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

	public FakeDatabase(Game g){
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
		accountId = accountExists(username, password);
		if(accountId == -1){
			return false;
		}
		else{
			//TODO: load game from id
				//go into other lists and pull out and set everything according to the accountId
			for(Map map : maps){
				if(map.getAccountId() == accountId){
					game.setMap(map);
					break;	//stop looking through list
				}
			}
			List<Actor> list = new ArrayList<Actor>();
			for(Actor actor : actors){
				if(actor.getAccountId() == accountId){
					actor.setLocation(game.getMap().getTile(actor.getLocation().getX(), actor.getLocation().getY()));
					list.add(actor);
				}
			}
			
			//TODO: don't forget to set actor location once map is found
			return true;
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
			actors.add(player);	//TODO: set defaults for player
			game = new Game(map, actors, null);
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

	public void writeCSV(){		//aka saveGame
		//TODO: compile all data into strings
		//TODO: Obtain data from necessary classes, rather than using the out dated lists in this class
		try {
			PrintWriter accountswriter = new PrintWriter(new File("src/Account.csv"));
			PrintWriter mapswriter = new PrintWriter(new File("src/Map.csv"));
			PrintWriter actorswriter = new PrintWriter(new File("src/Actor.csv"));
			StringBuilder accountbuilder = new StringBuilder();
			StringBuilder mapbuilder = new StringBuilder();
			StringBuilder actorbuilder = new StringBuilder();
			//TODO: mix inventory with actors, since each actor has their own inventory
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
