package ycp.edu.cs320.adventure.game;

import java.util.ArrayList;
import java.util.List;

// Games are all of the information that makes up each game
public class Game {
	private int gameId;
	private Map map;
	private Player player;
	private List<Creature> creatures;
	private List<Item> items;
	private List<Command> commands;
	private String gameLog;

	// Parameterless Constructor
	public Game() {
		gameId = 0;
		map = new Map();
		player = new Player();
		creatures = new ArrayList<Creature>();
		items = new ArrayList<Item>();
		commands = new ArrayList<Command>();
		gameLog = "You decided to set out on an Adventure!";
	}
	
	// Constructor
	public Game(int gameId, Map map, Player player, List<Creature> creatures, List<Item> items, List<Command> commands) {
		this.gameId = gameId;
		this.map = map;
		this.player = player;
		this.creatures = creatures;
		this.items = items;
		this.commands = commands;
		gameLog = "You decided to set out on an Adventure!";
	}
	
	// Sets the gameId
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	// Gets the gameId
	public int getGameId() {
		return gameId;
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
	
	// Sets gameLog object
	public void setGameLog(String gameLog){
		this.gameLog = gameLog;
	}
	
	// Gets gameLog object
	public String getGameLog(){
		return gameLog;
	}
}
