package derbyDatabase;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

public class TestItem {
	DerbyDatabase database;
	Item item;
	Item item2;
	int game_id;
	
	@Before
	public void setUp() {
		database = new DerbyDatabase();
		game_id = -999;
		item = new Item();
		item2 = new Item();
		
		//setting defaults
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
	}

	@Test
	public void testEverything() {
		//create items based on defaults
		int create = database.createItem(item.getGameId(), item.getName(), item.getDescription(), item.getWeight(), item.getDamage(), item.getHealth(), item.getQuestId(), item.getValue());
		int create2 = database.createItem(item2.getGameId(), item2.getName(), item2.getDescription(), item2.getWeight(), item2.getDamage(), item2.getHealth(), item2.getQuestId(), item2.getValue());
		assertTrue(create == create2 - 1);
		
		//get list of all items
		List<Item> items = database.getAllItems(game_id);
		//ensuring the recieved data is correct
		int size = items.size();
		int gameId = items.get(0).getGameId();
		String name = items.get(0).getName();
		String description = items.get(0).getDescription();
		int weight = items.get(0).getWeight();
		int damage = items.get(0).getDamage();
		int health = items.get(0).getHealth();
		int questId = items.get(0).getQuestId();
		int value = items.get(0).getValue();
		int itemId = items.get(0).getItemId();
		int itemId2 = items.get(1).getItemId();
		assertEquals(2, size);
		assertEquals(game_id, gameId);
		assertEquals("test", name);
		assertEquals("this is a test", description);
		assertEquals(100, weight);
		assertEquals(9001, damage);
		assertEquals(1234, health);
		assertEquals(-1, questId);
		assertEquals(7777, value);
		//Id's should be one apart
		assertEquals(itemId, itemId2-1);
		
		//individual updates
		boolean updatename = database.updateItemName(itemId, "New Name");
		boolean updatedescription = database.updateItemDescription(itemId, "New Description");
		boolean updateweight = database.updateItemWeight(itemId, 0);
		boolean updatedamage = database.updateItemDamage(itemId, 1);
		boolean updatehealth = database.updateItemHealth(itemId, 2);
		boolean updatequestid = database.updateItemQuestId(itemId, 3);
		boolean updatevalue = database.updateItemValue(itemId, 4);
		assertEquals(true, updatename);
		assertEquals(true, updatedescription);
		assertEquals(true, updateweight);
		assertEquals(true, updatedamage);
		assertEquals(true, updatehealth);
		assertEquals(true, updatequestid);
		assertEquals(true, updatevalue);
		
		//testing updates and getItem
		Item item3 = database.getItem(itemId);
		//ensuring the recieved data is correct
		int gameId2 = item3.getGameId();
		String name2 = item3.getName();
		String description2 = item3.getDescription();
		int weight2 = item3.getWeight();
		int damage2 = item3.getDamage();
		int health2 = item3.getHealth();
		int questId2 = item3.getQuestId();
		int value2 = item3.getValue();
		int itemId3 = item3.getItemId();
		assertEquals(game_id, gameId2);
		assertEquals("New Name", name2);
		assertEquals("New Description", description2);
		assertEquals(0, weight2);
		assertEquals(1, damage2);
		assertEquals(2, health2);
		assertEquals(3, questId2);
		assertEquals(4, value2);
		//the same items as before
		assertEquals(itemId, itemId3);
		
		//removing all items from the game
		List<Item> items2 = database.getAllItems(game_id);
		for(int i = 0; i < items2.size(); i++){
			boolean remove = database.removeItem(items.get(i).getItemId());
			assertEquals(true, remove);
		}
		
		//ensuring there are no more items to this game
		List<Item> items3 = database.getAllItems(game_id);
		int size2 = items3.size();
		assertEquals(0, size2);
	}
}
