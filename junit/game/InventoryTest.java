package game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import ycp.edu.cs320.adventure.game.Inventory;
import ycp.edu.cs320.adventure.game.Item;

public class InventoryTest {
	private Inventory inventory;
	private List<Item> items0;
	private List<Item> items1;
	private List<Item> items2;
	private Item item1;
	private Item item2;
	
	@Before
	public void setUp() {
		item1 = new Item("Stick", "Small Branch", 0, 1, 0, 5, 10, 0, 0);
		item2 = new Item("Big Stick", "Bigger Branch", 0, 2, 2, 10, 20, 0, 0);
		
		items0 = new ArrayList<Item>();
		
		items1 = new ArrayList<Item>();
		items1.add(item1);
		
		items2 = new ArrayList<Item>();
		items2.add(item1);
		items2.add(item2);
		
		inventory = new Inventory(items1);
	}
	
	@Test
	public void testGetInventory() {
		assertEquals(items1, inventory.getInventory());
	}
	
	@Test
	public void testSetInventory() {
		inventory.setInventory(items2);
		assertEquals(items2, inventory.getInventory());
	}
	
	@Test
	public void testAddToInventory() {
		inventory.setInventory(items0);
		inventory.addToInventory(item1);
		assertEquals(items1, inventory.getInventory());
	}
	
	@Test
	public void testAddMultipleToInventory() {
		inventory.setInventory(items0);
		inventory.addMultipleToInventory(items2);
		assertEquals(items2, inventory.getInventory());
	}
	
	@Test
	public void testRemoveFromInventory() {
		inventory.setInventory(items0);
		inventory.setInventory(items2);
		inventory.removeFromInventory(item2);
		assertEquals(items1, inventory.getInventory());
	}
}
