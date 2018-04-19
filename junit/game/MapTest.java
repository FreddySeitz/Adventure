package game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.database.InitialData;
import ycp.edu.cs320.adventure.game.Game;
import ycp.edu.cs320.adventure.game.GameEngine;
import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Player;
import ycp.edu.cs320.adventure.game.Tile;
import ycp.edu.cs320.adventure.game.Creature;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Command;

public class MapTest {
	private Map map;
	private Game game;
	private GameEngine engine;

	@Before
	public void setUp() {
		map = new Map();
		game = new Game(1, map, new Player(), new ArrayList<Creature>(), new ArrayList<Item>(), new ArrayList<Command>());
		engine = new GameEngine();
		game.setItems(engine.defaultItemList(10));
		engine.setGame(game);
	}

	@Test
	public void testGameId() {
		map.setGameId(2);
		assertTrue(map.getGameId() == 2);
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
		assertEquals(1, map.getMap()[2][1].getX());	//testing tile type for one of the tiles
	}
}
