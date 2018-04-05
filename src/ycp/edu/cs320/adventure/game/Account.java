package ycp.edu.cs320.adventure.game;

import java.util.ArrayList;
import java.util.List;

// Accounts are unique to each user, storing games and characters
public class Account {
	private int id;	//necessary to connect account to multiple maps (or game models) in the database
	private String username;
	private String password;
	private List<Player> characters;
	private List<Game> games;
	
	// Parameterless Constructor
	public Account() {
		characters = new ArrayList<Player>();
		games = new ArrayList<Game>();
	}
	
	// Constructor
	public Account(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	//sets id
	public void setId(int id){
		this.id = id;
	}
	
	//gets id
	public int getId(){
		return id;
	}
	
	// Sets username
	public void setUsername(String username) {
		this.username = username;
	}
	
	// Gets username
	public String getUsername(){
		return username;
	}
	
	// Sets password
	public void setPassword(String password) {
		this.password = password;
	}
		
	// Gets password
	public String getPassword(){
		return password;
	}
	
	// Sets characters for an Account
	public void setCharacters(List<Player> characters) {
		this.characters = characters;
	}
			
	// Returns characters for an Account
	public List<Player> getCharacters() {
		return characters;
	}
	
	// Sets the games for an Account
	public void setGames(List<Game> games){
		this.games = games;
	}
	
	// Gets the games for an Account
	public List<Game> getGames(){
		return games;
	}
	
	public void login() {
		throw new UnsupportedOperationException();
	}
	
	public void logout() {
		throw new UnsupportedOperationException();
	}
	
	public void createNewGame() {
		GameEngine.createNewGame(this.username, this.password);
	}
}
