package ycp.edu.cs320.adventure.game;

import java.util.ArrayList;
import java.util.List;

// Games are all of the information that makes up each game
public class Game {
	private Map map;
	private Player player;
	private List<Creature> creatures;
	private List<Item> items;
	private List<Command> commands;

	// Parameterless Constructor
	public Game() {
		map = new Map();
		player = new Player();
		creatures = new ArrayList<Creature>();
		items = new ArrayList<Item>();
		commands = new ArrayList<Command>();
	}
	
	// Constructor
	public Game(Map map, Player player, List<Creature> creatures, List<Item> items, List<Command> commands) {
		this.map = map;
		this.player = player;
		this.creatures = creatures;
		this.items = items;
		this.commands = commands;
	}

	// Sets the game map
	public void setMap(Map map) {
		this.map = map;
	}

	// Gets the game map
	public Map getMap() {
		return map;
	}

	// Sets the game player
	public void setPlayer(Player player) {
		this.player = player;
	}

	// Gets the game player
	public Player getPlayer(){
		return player;
	}

	// Sets the game creatures
	public void setCreatures(List<Creature> creatures) {
		this.creatures = creatures;
	}

	// Gets the game creatures
	public List<Creature> getCreatures(){
		return creatures;
	}

	// Sets the game items
	public void setItems(List<Item> items) {
		this.items = items;
	}

	// Gets the game items
	public List<Item> getItems(){
		return items;
	}

	// Sets the game items
	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	// Gets the game items
	public List<Command> getCommands(){
		return commands;
	}
}
