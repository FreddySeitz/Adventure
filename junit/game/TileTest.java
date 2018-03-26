package game;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import database.InitialData;
import game.Tile;

public class TileTest {
	private Tile tile;
	
	@Before
	public void setUp() {
		tile = new Tile();
	}
	
	@Test
	public void testType() {
		tile.setType(1);
		assertTrue(tile.getType() == 1);
	}
	
	@Test
	public void testX() {
		tile.setX(1);
		assertTrue(tile.getX() == 1);
	}
	
	@Test
	public void testY() {
		tile.setY(1);
		assertTrue(tile.getY() == 1);
	}
	
	@Test
	public void testItems() {
		tile = new Tile();
		tile.addItem(new Item("name", "test", 1, 1, 1, 1, 1, 1, 1));
		tile.addItem(new Item("name2", "test2", 2, 2, 2, 2, 2, 2, 2));
		assertTrue(tile.getItemList().size() == 2);
		assertTrue(tile.getItemList().get(0).getName() == "name");
		assertTrue(tile.getItemList().get(1).getDescription() == "test2");
	}
}
