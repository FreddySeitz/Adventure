package game;

import static org.junit.Assert.assertEquals;

import org.junit.*;

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
		assertEquals(item.getName(), test);
	}
	
	@Test
	public void testSetName() {
		String test = "hhghf8y47uda.v.vxnb";
		item.setName(test);
		assertEquals(item.getName(), test);
	}
	
	@Test
	public void testGetDescription() {
		String test = "CS320 is fun!";
		assertEquals(item.getDescription(), test);
	}
	
	@Test
	public void testSetDescription() {
		String test = "hahdhfahjdpfho";
		item.setDescription(test);
		assertEquals(item.getDescription(), test);
	}
	
	@Test
	public void testGetItemId() {
		int test = 5;
		assertEquals(item.getId(), test);
	}
	
	@Test
	public void testSetItemId() {
		int test = 8;
		item.setId(test);
		assertEquals(item.getId(), test);
	}
	
	@Test
	public void testGetWeight() {
		int test = 5;
		assertEquals(item.getWeight(), test);
	}
	
	@Test
	public void testSetWeight() {
		int test = 8;
		item.setWeight(test);
		assertEquals(item.getWeight(), test);
	}
	
	@Test
	public void testGetDamage() {
		int test = 5;
		assertEquals(item.getDamage(), test);
	}
	
	@Test
	public void testSetDamage() {
		int test = 8;
		item.setDamage(test);
		assertEquals(item.getDamage(), test);
	}
	
	@Test
	public void testGetHealth() {
		int test = 5;
		assertEquals(item.getHealth(), test);
	}
	
	@Test
	public void testSetHealth() {
		int test = 8;
		item.setHealth(test);
		assertEquals(item.getHealth(), test);
	}
	
	@Test
	public void testGetQuest_id() {
		int test = 5;
		assertEquals(item.getQuestId(), test);
	}
	
	@Test
	public void testSetQuest_id() {
		int test = 8;
		item.setQuestId(test);
		assertEquals(item.getQuestId(), test);
	}
	
	@Test
	public void testGetValue() {
		int test = 5;
		assertEquals(item.getValue(), test);
	}
	
	@Test
	public void testSetValue() {
		int test = 8;
		item.setValue(test);
		assertEquals(item.getValue(), test);
	}
}
