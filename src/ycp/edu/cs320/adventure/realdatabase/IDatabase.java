package ycp.edu.cs320.adventure.realdatabase;

import java.util.List;

import ycp.edu.cs320.adventure.game.GameEngine;
import ycp.edu.cs320.adventure.game.Item;

public interface IDatabase {
	
	public boolean createAccount(String username, String password);
	public boolean updateAccountAll(String username, String password, int account_id);
	public boolean updateAccountUsername(String username, int account_id);
	public boolean updateAccountPassword(String password, int account_id);
	public int getAccount(String username);
	
	public boolean createGame(int account_id);
	public List<Integer> getGame(int account_id);
	public boolean removeGame(int game_id);
	
	public boolean createItem(int game_id, String name, String description, int weight, int damage, int health, int quest_id, int value);
	public boolean updateItemAll(int item_id, int game_id, String name, String description, int weight, int damage, int health, int quest_id, int value);
	public boolean updateItemName(int item_id, String name);
	public boolean updateItemDescription(int item_id, String description);
	public boolean updateItemWeight(int item_id, String weight);
	public boolean updateItemDamage(int item_id, String damage);
	public boolean updateItemHealth(int item_id, String health);
	public boolean updateItemQuestId(int item_id, String quest_id);
	public boolean updateItemValue(int item_id, String value);
	public boolean removeItem(int item_id);
	public Item getItem(int item_id);
	public List<Item> getAllItems(int game_id);
	
	
//	public boolean loadGame(String username, String password, GameEngine engine);
//	public boolean newAccount(String username, String password, GameEngine engine);
//	public boolean newGame(String username, GameEngine engine);
//	public int accountExists(String username);
//	public boolean login(String username, String password);
}
