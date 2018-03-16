package game;

import java.util.List;

// Accounts are unique to each user, storing games and characters
public class Account {
	private int id;	//necessary to connect account to multiple maps (or game models) in the database
	private String username;
	private String password;
	private List<Player> characters;
	private List<Game> games;
	
	public Account(String u, String p){
		this.username = u;
		this.password = p;
	}
	
	//sets id
	public void setId(int i){
		id = i;
	}
	
	//gets id
	public int getId(){
		return id;
	}
	
	// Sets username
	public void setUsername(String u) {
		this.username = u;
	}
	
	// Gets username
	public String getUsername(){
		return username;
	}
	
	// Sets password
	public void setPassword(String p) {
		this.password = p;
	}
		
	// Gets password
	public String getPassword(){
		return password;
	}
	
	// Sets characters for an Account
	public void setCharacters(List<Player> c) {
		this.characters = c;
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
	
	public void createNewGame(Map map, List<Actor> actors, List<Item> items) {
		games.add(new Game(map, actors, items));
	}
}
