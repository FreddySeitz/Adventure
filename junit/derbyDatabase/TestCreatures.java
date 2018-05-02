package derbyDatabase;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.game.Creature;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Tile;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

public class TestCreatures {
	DerbyDatabase database;
	int game_id;
	Creature creature;
	Creature creature2;
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
		
		creature = new Creature();
		creature2 = new Creature();
		
		creature.setBaseDamage(3);
		creature.setHealth(3);
		creature.setMovementSpeed(3);
	}
	
	@Test
	public void testEverything() {
		//create and get creature
		database.createItem(game_id, "item", "a thing", 1, 1, 1, 1, 1);
		database.createItem(game_id, "thing", "fly, you fools", 2, 2, 2, 2, 2);
		item = database.getAllItems(game_id).get(0);
		item2 = database.getAllItems(game_id).get(1);
		
		database.createTile(game_id, 0, true, "a place", 0, 0, 0);
		database.createTile(game_id, 1, true, "a different place", 1, 1, 1);
		tile = database.getTile(game_id, 0, 0);
		tile2 = database.getTile(game_id, 1, 1);
		
		database.createCreature(game_id, item.getItemId(), 3, 0, 0, 3, 3);
		
		creature = database.getAllCreatures(game_id).get(0);
		assertEquals(1, creature.getEquippedItem().getDamage());
		assertEquals(3, creature.getBaseDamage());
		assertEquals(0, creature.getLocation().getType());
		
		//updating
		database.updateCreatureBaseDamage(creature.getCreatureId(), 10);
		database.updateCreatureEquippedItem(creature.getCreatureId(), item2.getItemId());
		database.updateCreatureHealth(creature.getCreatureId(), 10);
		database.updateCreatureMoveSpeed(creature.getCreatureId(), 10);
		database.updateCreatureX(creature.getCreatureId(), 1);
		database.updateCreatureY(creature.getCreatureId(), 1);
		
		creature2 = database.getAllCreatures(game_id).get(0);
		
		assertEquals(2, creature2.getEquippedItem().getDamage());
		assertEquals(10, creature2.getBaseDamage());
		assertEquals(10, creature2.getMovementSpeed());
		assertEquals(10, creature2.getHealth());
		assertEquals(1, creature2.getLocation().getType());
		
		//getting all creatures
		database.createCreature(game_id, creature2.getEquippedItem().getItemId(), creature2.getHealth(), creature2.getLocation().getX(), creature2.getLocation().getY(), creature2.getBaseDamage(), creature2.getMovementSpeed());
		creature2 = database.getAllCreatures(game_id).get(1);
		List<Creature> list = database.getAllCreatures(game_id);
		
		assertEquals(2, list.size());
		
		//all creatures at one location
		List<Creature> list2 = database.getAllCreaturesAtLocation(game_id,0,0);
		assertEquals(0, list2.size());
		
		database.updateCreatureX(creature.getCreatureId(), 0);
		database.updateCreatureY(creature.getCreatureId(), 0);
		database.updateCreatureX(creature2.getCreatureId(), 0);
		database.updateCreatureY(creature2.getCreatureId(), 0);
		List<Creature> list3 = database.getAllCreaturesAtLocation(game_id,0,0);
		assertEquals(2, list3.size());
		
		//removing
		database.removeCreature(creature.getCreatureId());
		database.removeCreature(creature2.getCreatureId());
		
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
