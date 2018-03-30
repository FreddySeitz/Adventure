package ycp.edu.cs320.adventure.game;

import java.util.List;

// Games are all of the information that makes up each game
public class Game {
	private Map map;
	private Actor player;
	private List<Actor> creatures;
	private List<Item> items;
	private List<Command> commands;

	public Game(Map map, Actor player, List<Actor> creatures, List<Item> items, List<Command> commands) {
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
	public void setPlayer(Actor player) {
		this.player = player;
	}

	// Gets the game player
	public Actor getPlayer(){
		return player;
	}

	// Sets the game creatures
	public void setCreatures(List<Actor> creatures) {
		this.creatures = creatures;
	}

	// Gets the game creatures
	public List<Actor> getCreatures(){
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
