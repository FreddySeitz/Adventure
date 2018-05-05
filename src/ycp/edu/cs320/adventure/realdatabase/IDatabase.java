package ycp.edu.cs320.adventure.realdatabase;

import java.util.List;

import ycp.edu.cs320.adventure.game.Creature;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Player;
import ycp.edu.cs320.adventure.game.Tile;

public interface IDatabase {
	//accounts
	public boolean createAccount(String username, String password);
	public boolean updateAccountAll(String username, String password, int account_id);
	public boolean updateAccountUsername(String username, int account_id);
	public boolean updateAccountPassword(String password, int account_id);
	public boolean removeAccount(String username);
	public int getAccount(String username);
	public String getAccountUsername(int account_id);
	public String getAccountPassword(int account_id);
	public boolean accountExists(String username);
	public boolean accountVerify(String username, String password);
	
	//games
	public int createGame(int account_id);
	public List<Integer> getGames(int account_id);
	public boolean removeGame(int game_id);
	
	public int countAllGames();
	
	//items
	public int createItem(int game_id, String name, String description, int weight, int damage, int health, int quest_id, int value);
	public boolean updateItemAll(int item_id, int game_id, String name, String description, int weight, int damage, int health, int quest_id, int value);
	public boolean updateItemName(int item_id, String name);
	public boolean updateItemDescription(int item_id, String description);
	public boolean updateItemWeight(int item_id, int weight);
	public boolean updateItemDamage(int item_id, int damage);
	public boolean updateItemHealth(int item_id, int health);
	public boolean updateItemQuestId(int item_id, int quest_id);
	public boolean updateItemValue(int item_id, int value);
	public boolean removeItem(int item_id);
	public Item getItem(int item_id);
	public List<Item> getAllItems(int game_id);
	
	//inventories
	public boolean addToPlayerInventory(int player_id, int item_id);
	public boolean existsPlayerInventoryItem(int player_id, int item_id);
	public boolean removeFromPlayerInventory(int player_id, int inventory_id);
	public boolean removeAllFromPlayerInventory(int player_id);
	public List<Item> getPlayerInventory(int player_id);
	
	public boolean addToCreatureInventory(int creature_id, int item_id);
	public boolean existsCreatureInventoryItem(int creature_id, int item_id);
	public boolean removeFromCreatureInventory(int creature_id, int inventory_id);
	public boolean removeAllFromCreatureInventory(int creature_id);
	public List<Item> getCreatureInventory(int creature_id);
	
	public boolean addToTileInventory(int tile_id, int item_id);
	public boolean existsTileInventoryItem(int tile_id, int item_id);
	public boolean removeFromTileInventory(int tile_id, int inventory_id);
	public boolean removeAllFromTileInventory(int tile_id);
	public List<Item> getTileInventory(int tile_id);
	
	public Item getInventoryItem(int inventory_id);
	
	//maps
	public int createMap(int game_id, int height, int width);
	public boolean updateMapAll(int game_id, int height, int width);
	public boolean updateMapHeight(int game_id, int height);
	public boolean updateMapWidth(int game_id, int width);
	public boolean removeMap(int game_id);
	public Map getMap(int game_id);
	
	//tiles
	public int createTile(int game_id, int type, boolean visible, boolean hidden, boolean active, boolean prompt, int question, String description, int damage, int x, int y);
	public boolean updateTileAll(int type, boolean visible, boolean hidden, boolean active, boolean prompt, int question, String description, int x, int y, int tile_id);
	public boolean updateTileType(int type, int tile_id);
	public boolean updateTileVisible(boolean visible, int tile_id);
	public boolean updateTileHidden(boolean hidden, int tile_id);
	public boolean updateTileActive(boolean active, int tile_id);
	public boolean updateTilePrompt(boolean prompt, int tile_id);
	public boolean updateTileQuestion(int question, int tile_id);
	public boolean updateTileDescription(String description, int tile_id);
	public boolean updateTileDamage(int damage, int tile_id);
	public boolean updateTileX(int x, int tile_id);
	public boolean updateTileY(int y, int tile_id);
	public boolean tileExists(int game_id, int x, int y);
	public boolean removeTile(int tile_id);
	public Tile getTile(int game_id, int x, int y);
	public List<Tile> getAllTiles(int game_id);
	
	//creatures
	public int createCreature(int game_id, int equippedItem, int health, int x, int y, int baseDamage, int moveSpeed);
	public boolean updateCreatureEquippedItem(int creature_id, int equippedItem);
	public boolean updateCreatureHealth(int creature_id, int health);
	public boolean updateCreatureX(int creature_id, int x);
	public boolean updateCreatureY(int creature_id, int y);
	public boolean updateCreatureBaseDamage(int creature_id, int baseDamage);
	public boolean updateCreatureMoveSpeed(int creature_id, int moveSpeed);
	public boolean removeCreature(int creature_id);
	public Creature getCreature(int creature_id);
	public List<Creature> getAllCreatures(int game_id);
	
	public List<Creature> getAllCreaturesAtLocation(int game_id, int x, int y);
	
	//players
	public int createPlayer(int game_id, int equippedItem, int health, int x, int y, int baseDamage, int score);
	public boolean updatePlayerEquippedItem(int player_id, int equippedItem);
	public boolean updatePlayerHealth(int player_id, int health);
	public boolean updatePlayerX(int player_id, int x);
	public boolean updatePlayerY(int player_id, int y);
	public boolean updatePlayerBaseDamage(int player_id, int baseDamage);
	public boolean updatePlayerScore(int player_id, int score);
	public boolean removePlayer(int player_id);
	public Player getPlayer(int game_id);
	
	//gameLogs
	public boolean addGameLog(int game_id, String text);
	public String getGameLog(int game_id);
	public List<String> getGameLogList(int game_id);
	public boolean removeGameLog(int game_id);
}
