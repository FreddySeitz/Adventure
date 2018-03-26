package game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import ycp.edu.cs320.adventure.game.Actor;
import ycp.edu.cs320.adventure.game.Inventory;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Tile;

public class ActorTest {
	private Actor actor;
	private Item item1;
	private Item item2;
	private Inventory inventory1;
	private Inventory inventory2;
	private Tile tile1;
	private Tile tile2;
	
	@Before
	public void setUp() {
		actor = new Actor();
		actor.setAccountId(0);
		actor.setBaseDamage(1);
		actor.setHealth(100);
		
		item1 = new Item("Stick", "Small Branch", actor.getAccountId(), 1, 0, 5, 10, 0, 0);
		item2 = new Item("Big Stick", "Bigger Branch", actor.getAccountId(), 2, 2, 10, 20, 0, 0);
		
		List<Item> items = new ArrayList<Item>();
		items.add(item1);
		inventory1 = new Inventory(items);
		items.add(item2);
		inventory2 = new Inventory(items);
		
		tile1 = new Tile();
		tile1.setType(1);
		tile1.setX(0);
		tile1.setY(0);
		
		tile2 = new Tile();
		tile2.setType(2);
		tile2.setX(1);
		tile2.setY(1);
		
		actor.setEquippedItem(item1);
		actor.setInventory(inventory1);
		actor.setLocation(tile1);
	}
	
	@Test
	public void testGetAccountId() {
		assertEquals(0, actor.getAccountId());
	}
	
	@Test
	public void testSetAccountId() {
		actor.setAccountId(1);
		assertEquals(1, actor.getAccountId());
	}
	
	@Test
	public void testGetBaseDamage() {
		assertEquals(1, actor.getBaseDamage());
	}
	
	@Test
	public void testSetBaseDamage() {
		actor.setBaseDamage(2);
		assertEquals(2, actor.getBaseDamage());
	}
	
	@Test
	public void testGetEquippedItem() {
		assertEquals(item1, actor.getEquippedItem());
	}
	
	@Test
	public void testSetEqiuppedItem() {
		actor.setEquippedItem(item2);
		assertEquals(item2, actor.getEquippedItem());
	}
	
	@Test
	public void testGetHealth() {
		assertEquals(100, actor.getHealth());
	}
	
	@Test
	public void testSetHealth() {
		actor.setHealth(90);
		assertEquals(90, actor.getHealth());
	}
	
	@Test
	public void testGetInventory() {
		assertEquals(inventory1, actor.getInventory());
	}
	
	@Test
	public void testSetInventory() {
		actor.setInventory(inventory2);
		assertEquals(inventory2, actor.getInventory());
	}
	
	@Test
	public void testGetLocation() {
		assertEquals(tile1, actor.getLocation());
	}
	
	@Test
	public void testSetLocation() {
		actor.setLocation(tile2);
		assertEquals(tile2, actor.getLocation());
	}
}
