package derbyDatabase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

public class TestMap {
	DerbyDatabase database;
	int game_id;
	int height;
	int width;
	Map map;
	
	@Before
	public void setUp() {
		database = new DerbyDatabase();
		game_id = -999;
		height = 20;
		width = 20;
		map = new Map();
	}
	
	@Test
	public void testEverything() {
		//create map
		database.createMap(game_id, height, width);
		
		//testing if game was created
		map = database.getMap(game_id);
		assertEquals(20, map.getHeight());
		assertEquals(20, map.getWidth());
		
		//testing update
		boolean update = database.updateMapHeight(game_id, 5);
		boolean update2 = database.updateMapWidth(game_id, 5);
		assertEquals(true, update);
		assertEquals(true, update2);
		
		//testing update worked
		map = database.getMap(game_id);
		assertEquals(5, map.getHeight());
		assertEquals(5, map.getWidth());
		
		//testing map update all
		boolean update3 = database.updateMapAll(game_id, 10, 10);
		assertEquals(true, update3);
		
		//testing update worked
		map = database.getMap(game_id);
		assertEquals(10, map.getHeight());
		assertEquals(10, map.getWidth());
		
		//removeMap
		boolean remove = database.removeMap(game_id);
		assertEquals(true, remove);
	}
}
