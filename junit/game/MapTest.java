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
		game = new Game(map, new Player(), new ArrayList<Creature>(), new ArrayList<Item>(), new ArrayList<Command>());
		engine = new GameEngine();
		game.setItems(engine.defaultItemList(10));
		engine.setGame(game);
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
		map.buildDefault(engine);
		assertTrue(map.getHeight() == 20);
		assertTrue(map.getWidth() == 20);
		assertEquals(1, map.getMap()[2][1].getX());	//testing tile type for one of the tiles

		//treasure test
		int count = 0;
		int countTreasure = 0;
		for(int i = 0; i < 20; i++){	//counts the number of treasure on the whole map
			for(int j = 0; j < 20; j++){
				for(int k = 0; k < map.getTile(i, j).getItemList().size(); k++){
					count++;
					if(map.getTile(i, j).getItemList().get(k).getValue() >= 100){
						countTreasure++;
					}
				}
			}
		}
		assertTrue(count == 20);	//counts 20 items
		assertTrue(countTreasure == 20);	//counts 20 treasure
		assertTrue(count == countTreasure);	//all items should be treasure
	}
	
	@Test
	public void testDecompileAndCompile() {
		map.buildDefault(engine);
		String compiled = map.compileTiles();
		//System.out.println(compiled);
		
		Map map2 = new Map();
		map2.decompileTiles(compiled);
		
		boolean test = true;
		for(int i = 0; i < map.getHeight(); i++){
			for(int j = 0; j < map.getWidth(); j++){
				System.out.println(i);
				System.out.println(j + "\n");
				if(map.getTile(i, j).getType() != map2.getTile(i, j).getType()){
					System.out.println("Broken");
					System.out.println(map.getTile(i, j).getType());
					System.out.println(map2.getTile(i, j).getType() + "/n");
					test = false;
				}
				else{
					System.out.println(map.getTile(i, j).getType());
					System.out.println(map2.getTile(i, j).getType() + "/n");
				}
			}
		}
		assertEquals(true, test);
	}
}
