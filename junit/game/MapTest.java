package game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import database.InitialData;
import game.Tile;

public class MapTest {
	private Map map;
	
	@Before
	public void setUp() {
		map = new Map();
	}
	
	@Test
	public void testAccountId() {
		map.setAccountId(1);
		assertTrue(map.getAccountId() == 1);
	}
	
	@Test
	public void testHeight() {
		map.setHeight(20);
		assertTrue(map.getHeight() == 20);
	}
	
	@Test
	public void testWidth() {
		map.setWidth(20);
		assertTrue(map.getWidth() == 20);
	}
	
	@Test
	public void testBuildDefault() {
		map.buildDefault();
		assertTrue(map.getHeight() == 20);
		assertTrue(map.getWidth() == 20);
		assertEquals(1, map.getMap()[2][1].getX());
		
	}
}
