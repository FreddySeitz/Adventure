package ycp.edu.cs320.adventure.game;

import java.util.List;

// Games are all of the information that makes up each game
public class Game {
	private Map map;
	private List<Actor> actors;
	private List<Item> items;
	
	public Game(Map map, List<Actor> actors, List<Item> items) {
		this.map = map;
		this.actors = actors;
		this.items = items;
	}
	
	// Sets the game map
	public void setMap(Map map) {
		this.map = map;
	}
	
	// Gets the game map
	public Map getMap() {
		return map;
	}
	
	// Sets the game actors
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	
	// Gets the game actors
	public List<Actor> getActors(){
		return actors;
	}
	
	// Sets the game items
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	// Gets the game items
	public List<Item> getItems(){
		return items;
	}
}