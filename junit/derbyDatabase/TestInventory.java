package derbyDatabase;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.game.Creature;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Player;
import ycp.edu.cs320.adventure.game.Tile;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

public class TestInventory {

	DerbyDatabase database;
	Item item;
	Item item2;
	Item item3;
	int game_id;
	Player player;
	Creature creature;
	Creature creature2;
	Tile tile;
	Tile tile2;

	@Before
	public void setUp() {
		database = new DerbyDatabase();
		item = new Item();
		item2 = new Item();
		item3 = new Item();
		game_id = -999;
		player = new Player();
		creature = new Creature();
		creature2 = new Creature();
		tile = new Tile();
		tile2 = new Tile();

		item.setGameId(game_id);
		item.setName("test");
		item.setDescription("this is a test");
		item.setWeight(100);
		item.setDamage(9001);
		item.setHealth(1234);
		item.setQuestId(-1);
		item.setValue(7777);

		item2.setGameId(game_id);
		item2.setName("floof");
		item2.setDescription("it kinda flies");
		item2.setWeight(1);
		item2.setDamage(6);
		item2.setHealth(7);
		item2.setQuestId(-2);
		item2.setValue(1928);

		item3.setGameId(game_id);
		item3.setName("sword");
		item3.setDescription("swinging thing");
		item3.setWeight(5);
		item3.setDamage(15);
		item3.setHealth(2);
		item3.setQuestId(4);
		item3.setValue(1);

		player.setGameId(game_id);
		player.setLocation(tile);
		creature.setGameId(game_id);
		creature2.setGameId(game_id);
		tile.setGameId(game_id);
		tile.setX(1);
		tile.setY(1);
		tile.setDamage(5);
		tile.setType(1);
		tile.setDescription("a tile");
		tile2.setGameId(game_id);
		tile2.setX(2);
		tile2.setY(2);
		tile2.setDamage(5);
		tile2.setType(1);
		tile.setDescription("another tile");
	}

	@Test
	public void testPlayerInventory() {
		//creating player and items in database
		database.createItem(game_id, item.getName(), item.getDescription(), item.getWeight(), item.getDamage(), item.getHealth(), item.getQuestId(), item.getValue());
		database.createItem(game_id, item2.getName(), item2.getDescription(), item2.getWeight(), item2.getDamage(), item2.getHealth(), item2.getQuestId(), item2.getValue());
		database.createItem(game_id, item3.getName(), item3.getDescription(), item3.getWeight(), item3.getDamage(), item3.getHealth(), item3.getQuestId(), item3.getValue());
		database.createTile(game_id, tile.getType(), true, false, false, false, 0, tile.getDescription(), tile.getDamage(), tile.getX(), tile.getY());
		database.createPlayer(game_id, item.getItemId(), 1, 1, 1, 1, 0);

		//retrieve items and player for the game
		int player_id = database.getPlayer(game_id).getPlayerId();
		List<Item> items = database.getAllItems(game_id);
		int item_id = items.get(0).getItemId();
		int item_id2 = items.get(1).getItemId();
		int item_id3 = items.get(2).getItemId();

		//adding items to player
		boolean add = database.addToPlayerInventory(player_id, item_id);
		boolean add2 = database.addToPlayerInventory(player_id, item_id);
		boolean add3 = database.addToPlayerInventory(player_id, item_id2);
		assertEquals(true, add);
		assertEquals(true, add2);
		assertEquals(true, add3);

		//testing itemExists in inventory
		boolean exists = database.existsPlayerInventoryItem(player_id, item_id);
		boolean exists2 = database.existsPlayerInventoryItem(player_id, item_id2);
		boolean exists3 = database.existsPlayerInventoryItem(player_id, item_id3);
		assertEquals(true, exists);
		assertEquals(true, exists2);
		assertEquals(false, exists3);	//item 3 was never added

		//testing getInventory, ensuring all items were input
		List<Item> inventory = database.getPlayerInventory(player_id);
		int size = inventory.size();
		Item inventoryItem = inventory.get(0);
		Item inventoryItem2 = inventory.get(1);
		Item inventoryItem3 = inventory.get(2);
		assertEquals(3, size);
		assertEquals(inventoryItem.getHealth(), inventoryItem2.getHealth()); //should be the same item
		assertTrue(inventoryItem.getDamage() != inventoryItem3.getDamage());  //not the same item

		//test itemRemove
		for(int i = 0; i < inventory.size(); i++){
			boolean remove = database.removeFromPlayerInventory(player_id, inventory.get(i).getInventoryId());
			assertEquals(true, remove);
			assertEquals(database.getPlayerInventory(player_id).size(), size-i-1);
		}

		//ensure items are gone
		List<Item> inventory2 = database.getPlayerInventory(player_id);
		int size2 = inventory2.size();
		assertEquals(0, size2);

		//removing everything else
		List<Item> items2 = database.getAllItems(game_id);
		for(int i = 0; i < items2.size(); i++){
			database.removeItem(items2.get(i).getItemId());
		}

		List<Tile> tiles = database.getAllTiles(game_id);
		for(int i = 0; i < tiles.size(); i++){
			database.removeTile(tiles.get(i).getTileId());
		}
		
		database.removePlayer(player_id);
	}

	@Test
	public void testCreatureInventory() {
		//creating creatures and items in database
		database.createItem(game_id, item.getName(), item.getDescription(), item.getWeight(), item.getDamage(), item.getHealth(), item.getQuestId(), item.getValue());
		database.createItem(game_id, item2.getName(), item2.getDescription(), item2.getWeight(), item2.getDamage(), item2.getHealth(), item2.getQuestId(), item2.getValue());
		database.createItem(game_id, item3.getName(), item3.getDescription(), item3.getWeight(), item3.getDamage(), item3.getHealth(), item3.getQuestId(), item3.getValue());
		database.createTile(game_id, tile.getType(), true, false, false, false, 0, tile.getDescription(), tile.getDamage(), tile.getX(), tile.getY());
		database.createTile(game_id, tile2.getType(), true, false, false, false, 0, tile2.getDescription(), tile2.getDamage(), tile2.getX(), tile2.getY());
		database.createCreature(game_id, item.getItemId(), 1, 1, 1, 1, 1);
		database.createCreature(game_id, item.getItemId(), 2, 2, 2, 2, 1);

		//retrieve items and creatures for the game
		List<Creature> creatures = database.getAllCreatures(game_id);
		int creature_id = creatures.get(0).getCreatureId();
		int creature_id2 = creatures.get(1).getCreatureId();
		List<Item> items = database.getAllItems(game_id);
		int item_id = items.get(0).getItemId();
		int item_id2 = items.get(1).getItemId();
		int item_id3 = items.get(2).getItemId();

		//adding items to creatures
		boolean add = database.addToCreatureInventory(creature_id, item_id);
		boolean add2 = database.addToCreatureInventory(creature_id, item_id);
		boolean add3 = database.addToCreatureInventory(creature_id, item_id2);
		boolean add4 = database.addToCreatureInventory(creature_id2, item_id3);
		boolean add5 = database.addToCreatureInventory(creature_id2, item_id3);
		boolean add6 = database.addToCreatureInventory(creature_id2, item_id2);
		assertEquals(true, add);
		assertEquals(true, add2);
		assertEquals(true, add3);
		assertEquals(true, add4);
		assertEquals(true, add5);
		assertEquals(true, add6);

		//testing itemExists in inventory
		boolean exists = database.existsCreatureInventoryItem(creature_id, item_id);
		boolean exists2 = database.existsCreatureInventoryItem(creature_id, item_id2);
		boolean exists3 = database.existsCreatureInventoryItem(creature_id, item_id3);
		boolean exists4 = database.existsCreatureInventoryItem(creature_id2, item_id);
		boolean exists5 = database.existsCreatureInventoryItem(creature_id2, item_id2);
		boolean exists6 = database.existsCreatureInventoryItem(creature_id2, item_id3);
		assertEquals(true, exists);
		assertEquals(true, exists2);
		assertEquals(false, exists3);	//item 3 was never added to creature 1
		assertEquals(false, exists4);	//item 1 was never added to creature 2
		assertEquals(true, exists5);
		assertEquals(true, exists6);

		//testing getInventory, ensuring all items were input
		List<Item> inventory = database.getCreatureInventory(creature_id);
		List<Item> inventory2 = database.getCreatureInventory(creature_id2);
		int size = inventory.size();
		int size2 = inventory2.size();
		Item inventoryItem1 = inventory.get(0);
		Item inventoryItem12 = inventory.get(1);
		Item inventoryItem13 = inventory.get(2);
		Item inventoryItem2 = inventory2.get(0);
		Item inventoryItem22 = inventory2.get(1);
		Item inventoryItem23 = inventory2.get(2);
		assertEquals(3, size);
		assertEquals(3, size2);
		assertEquals(size, size2);
		assertEquals(inventoryItem1.getHealth(), inventoryItem12.getHealth()); //should be the same item
		assertTrue(inventoryItem1.getDamage() != inventoryItem13.getDamage());  //not the same item
		assertEquals(inventoryItem2.getHealth(), inventoryItem22.getHealth()); //should be the same item
		assertTrue(inventoryItem2.getDamage() != inventoryItem23.getDamage());  //not the same item
		assertEquals(inventoryItem13.getHealth(), inventoryItem23.getHealth());  //same item from different creatures

		//test itemRemove
		for(int i = 0; i < inventory.size(); i++){
			boolean remove = database.removeFromCreatureInventory(creature_id, inventory.get(i).getInventoryId());
			assertEquals(true, remove);
			assertEquals(database.getCreatureInventory(creature_id).size(), size-i-1);
		}
		for(int i = 0; i < inventory2.size(); i++){
			boolean remove = database.removeFromCreatureInventory(creature_id2, inventory2.get(i).getInventoryId());
			assertEquals(true, remove);
			assertEquals(database.getCreatureInventory(creature_id2).size(), size2-i-1);
		}

		//ensure items are gone
		List<Item> inventory3 = database.getCreatureInventory(creature_id);
		int size3 = inventory3.size();
		assertEquals(0, size3);
		List<Item> inventory4 = database.getCreatureInventory(creature_id2);
		int size4 = inventory4.size();
		assertEquals(0, size4);

		//removing everything else
		List<Item> items2 = database.getAllItems(game_id);
		for(int i = 0; i < items2.size(); i++){
			database.removeItem(items2.get(i).getItemId());
		}
		
		List<Creature> creatures2 = database.getAllCreatures(game_id);
		for(int i = 0; i < creatures2.size(); i++){
			database.removeCreature(creatures2.get(i).getCreatureId());
		}

		List<Tile> tiles = database.getAllTiles(game_id);
		for(int i = 0; i < tiles.size(); i++){
			database.removeTile(tiles.get(i).getTileId());
		}
	}

	@Test
	public void testTileInventory() {
		//creating tiles and items in database
		database.createItem(game_id, item.getName(), item.getDescription(), item.getWeight(), item.getDamage(), item.getHealth(), item.getQuestId(), item.getValue());
		database.createItem(game_id, item2.getName(), item2.getDescription(), item2.getWeight(), item2.getDamage(), item2.getHealth(), item2.getQuestId(), item2.getValue());
		database.createItem(game_id, item3.getName(), item3.getDescription(), item3.getWeight(), item3.getDamage(), item3.getHealth(), item3.getQuestId(), item3.getValue());
		database.createTile(game_id, tile.getType(), true, false, false, false, 0, tile.getDescription(), tile.getDamage(), tile.getX(), tile.getY());
		database.createTile(game_id, tile2.getType(), true, false, false, false, 0, tile2.getDescription(), tile2.getDamage(), tile2.getX(), tile2.getY());

		//retrieve items and tiles for the game
		List<Tile> tiles = database.getAllTiles(game_id);
		int tile_id = tiles.get(0).getTileId();
		int tile_id2 = tiles.get(1).getTileId();
		List<Item> items = database.getAllItems(game_id);
		int item_id = items.get(0).getItemId();
		int item_id2 = items.get(1).getItemId();
		int item_id3 = items.get(2).getItemId();

		//adding items to creatures
		boolean add = database.addToTileInventory(tile_id, item_id);
		boolean add2 = database.addToTileInventory(tile_id, item_id);
		boolean add3 = database.addToTileInventory(tile_id, item_id2);
		boolean add4 = database.addToTileInventory(tile_id2, item_id3);
		boolean add5 = database.addToTileInventory(tile_id2, item_id3);
		boolean add6 = database.addToTileInventory(tile_id2, item_id2);
		assertEquals(true, add);
		assertEquals(true, add2);
		assertEquals(true, add3);
		assertEquals(true, add4);
		assertEquals(true, add5);
		assertEquals(true, add6);

		//testing itemExists in inventory
		boolean exists = database.existsTileInventoryItem(tile_id, item_id);
		boolean exists2 = database.existsTileInventoryItem(tile_id, item_id2);
		boolean exists3 = database.existsTileInventoryItem(tile_id, item_id3);
		boolean exists4 = database.existsTileInventoryItem(tile_id2, item_id);
		boolean exists5 = database.existsTileInventoryItem(tile_id2, item_id2);
		boolean exists6 = database.existsTileInventoryItem(tile_id2, item_id3);
		assertEquals(true, exists);
		assertEquals(true, exists2);
		assertEquals(false, exists3);	//item 3 was never added to tile 1
		assertEquals(false, exists4);	//item 1 was never added to tile 2
		assertEquals(true, exists5);
		assertEquals(true, exists6);

		//testing getInventory, ensuring all items were input
		List<Item> inventory = database.getTileInventory(tile_id);
		List<Item> inventory2 = database.getTileInventory(tile_id2);
		int size = inventory.size();
		int size2 = inventory2.size();
		Item inventoryItem1 = inventory.get(0);
		Item inventoryItem12 = inventory.get(1);
		Item inventoryItem13 = inventory.get(2);
		Item inventoryItem2 = inventory2.get(0);
		Item inventoryItem22 = inventory2.get(1);
		Item inventoryItem23 = inventory2.get(2);
		assertEquals(3, size);
		assertEquals(3, size2);
		assertEquals(size, size2);
		assertEquals(inventoryItem1.getHealth(), inventoryItem12.getHealth()); //should be the same item
		assertTrue(inventoryItem1.getDamage() != inventoryItem13.getDamage());  //not the same item
		assertEquals(inventoryItem2.getHealth(), inventoryItem22.getHealth()); //should be the same item
		assertTrue(inventoryItem2.getDamage() != inventoryItem23.getDamage());  //not the same item
		assertEquals(inventoryItem13.getHealth(), inventoryItem23.getHealth());  //same item from different tiles

		//test itemRemove
		for(int i = 0; i < inventory.size(); i++){
			boolean remove = database.removeFromTileInventory(tile_id, inventory.get(i).getInventoryId());
			assertEquals(true, remove);
			assertEquals(database.getTileInventory(tile_id).size(), size-i-1);
		}
		for(int i = 0; i < inventory2.size(); i++){
			boolean remove = database.removeFromTileInventory(tile_id2, inventory2.get(i).getInventoryId());
			assertEquals(true, remove);
			assertEquals(database.getTileInventory(tile_id2).size(), size2-i-1);
		}

		//ensure items are gone
		List<Item> inventory3 = database.getTileInventory(tile_id);
		int size3 = inventory3.size();
		assertEquals(0, size3);
		List<Item> inventory4 = database.getTileInventory(tile_id2);
		int size4 = inventory4.size();
		assertEquals(0, size4);

		//removing everything else
		List<Item> items2 = database.getAllItems(game_id);
		for(int i = 0; i < items2.size(); i++){
			database.removeItem(items2.get(i).getItemId());
		}
		
		List<Tile> tiles2 = database.getAllTiles(game_id);
		for(int i = 0; i < tiles2.size(); i++){
			database.removeTile(tiles2.get(i).getTileId());
		}
	}
}
