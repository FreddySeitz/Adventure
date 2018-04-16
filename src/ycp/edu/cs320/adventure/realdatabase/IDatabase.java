package ycp.edu.cs320.adventure.realdatabase;

import ycp.edu.cs320.adventure.game.GameEngine;

public interface IDatabase {
	public boolean loadGame(String username, String password, GameEngine engine);
	public boolean newAccount(String username, String password, GameEngine engine);
	public boolean newGame(String username, GameEngine engine);
	public int accountExists(String username);
	public boolean login(String username, String password);
	public void createAccount(String username, String password);
}
