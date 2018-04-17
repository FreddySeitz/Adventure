package game;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import ycp.edu.cs320.adventure.game.Item;

public class ItemTest {
	private Item item;
	
	@Before
	public void setUp() {
		String test1 = "CS320 is fun!";
		int test2 = 5;
		item = new Item(test1, test1, test2, test2, test2, test2, test2, test2, test2);
	}
	
	@Test
	public void testGetName() {
		String test = "CS320 is fun!";
		assertEquals(test, item.getName());
	}
	
	@Test
	public void testSetName() {
		String test = "hhghf8y47uda.v.vxnb";
		item.setName(test);
		assertEquals(test, item.getName());
	}
	
	@Test
	public void testGetDescription() {
		String test = "CS320 is fun!";
		assertEquals(test, item.getDescription());
	}
	
	@Test
	public void testSetDescription() {
		String test = "hahdhfahjdpfho";
		item.setDescription(test);
		assertEquals(test, item.getDescription());
	}
	
	@Test
	public void testGetGameId() {
		int test = 5;
		assertEquals(test, item.getItemId());
	}
	
	@Test
	public void testSetGameId() {
		int test = 8;
		item.setItemId(test);
		assertEquals(test, item.getItemId());
	}
	
	@Test
	public void testGetItemId() {
		int test = 5;
		assertEquals(test, item.getItemId());
	}
	
	@Test
	public void testSetItemId() {
		int test = 8;
		item.setItemId(test);
		assertEquals(test, item.getItemId());
	}
	
	@Test
	public void testGetWeight() {
		int test = 5;
		assertEquals(test, item.getWeight());
	}
	
	@Test
	public void testSetWeight() {
		int test = 8;
		item.setWeight(test);
		assertEquals(test, item.getWeight());
	}
	
	@Test
	public void testGetDamage() {
		int test = 5;
		assertEquals(test, item.getDamage());
	}
	
	@Test
	public void testSetDamage() {
		int test = 8;
		item.setDamage(test);
		assertEquals(test, item.getDamage());
	}
	
	@Test
	public void testGetHealth() {
		int test = 5;
		assertEquals(test, item.getHealth());
	}
	
	@Test
	public void testSetHealth() {
		int test = 8;
		item.setHealth(test);
		assertEquals(test, item.getHealth());
	}
	
	@Test
	public void testGetQuest_id() {
		int test = 5;
		assertEquals(test, item.getQuestId());
	}
	
	@Test
	public void testSetQuest_id() {
		int test = 8;
		item.setQuestId(test);
		assertEquals(test, item.getQuestId());
	}
	
	@Test
	public void testGetValue() {
		int test = 5;
		assertEquals(test, item.getValue());
	}
	
	@Test
	public void testSetValue() {
		int test = 8;
		item.setValue(test);
		assertEquals(test, item.getValue());
	}
}
