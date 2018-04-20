package derbyDatabase;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Player;
import ycp.edu.cs320.adventure.game.Tile;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

public class TestPlayer {
	DerbyDatabase database;
	int game_id;
	Player player;
	Player player2;
	Item item;
	Item item2;
	Tile tile;
	Tile tile2;

	@Before
	public void setUp() {
		database = new DerbyDatabase();
		game_id = -999;
		
		item = new Item();
		item2 = new Item();
		
		tile = new Tile();
		tile.setX(0);
		tile.setY(0);
		
		tile2 = new Tile();
		tile2.setX(1);
		tile2.setY(1);
		
		player = new Player();
		player2 = new Player();
		
		player.setBaseDamage(3);
		player.setHealth(3);
		player.setScore(3);
	}
	
	@Test
	public void testEverything() {
		//create and get player
		database.createItem(game_id, "item", "a thing", 1, 1, 1, 1, 1);
		database.createItem(game_id, "thing", "fly, you fools", 2, 2, 2, 2, 2);
		item = database.getAllItems(game_id).get(0);
		item2 = database.getAllItems(game_id).get(1);
		
		database.createTile(game_id, 0, "a place", 0, 0, 0);
		database.createTile(game_id, 1, "a different place", 1, 1, 1);
		tile = database.getTile(game_id, 0, 0);
		tile2 = database.getTile(game_id, 1, 1);
		
		database.createPlayer(game_id, item.getItemId(), 3, 0, 0, 3, 3);
		
		player = database.getPlayer(game_id);
		assertEquals(1, player.getEquippedItem().getDamage());
		assertEquals(3, player.getBaseDamage());
		assertEquals(0, player.getLocation().getType());
		
		//updating
		database.updatePlayerBaseDamage(player.getPlayerId(), 10);
		database.updatePlayerEquippedItem(player.getPlayerId(), item2.getItemId());
		database.updatePlayerHealth(player.getPlayerId(), 10);
		database.updatePlayerScore(player.getPlayerId(), 10);
		database.updatePlayerX(player.getPlayerId(), 1);
		database.updatePlayerY(player.getPlayerId(), 1);
		
		player = database.getPlayer(game_id);
		
		assertEquals(2, player.getEquippedItem().getDamage());
		assertEquals(10, player.getBaseDamage());
		assertEquals(10, player.getScore());
		assertEquals(10, player.getHealth());
		assertEquals(1, player.getLocation().getType());
		
		//removing
		database.removePlayer(player.getPlayerId());
		
		List<Item> items = database.getAllItems(game_id);
		for(int i = 0; i<items.size(); i++){
			database.removeItem(items.get(i).getItemId());
		}
		
		List<Tile> tiles = database.getAllTiles(game_id);
		for(int i = 0; i<tiles.size(); i++){
			database.removeTile(tiles.get(i).getTileId());
		}
		
	}
}
