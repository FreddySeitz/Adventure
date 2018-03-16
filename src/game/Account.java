package game;

import java.util.List;

// Accounts are unique to each user, storing games and characters
public class Account {
	private String username;
	private String password;
	private List<Player> characters;
	private List<Game> games;
	
	public Account(String u, String p){
		this.username = u;
		this.password = p;
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
}
