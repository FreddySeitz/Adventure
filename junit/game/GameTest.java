package game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import ycp.edu.cs320.adventure.game.Command;
import ycp.edu.cs320.adventure.game.Creature;
import ycp.edu.cs320.adventure.game.Game;
import ycp.edu.cs320.adventure.game.GameEngine;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Player;

public class GameTest {
	private List<Player> characters1;
	private List<Player> characters2;
	private List<Creature> creatures1;
	private List<Creature> creatures2;
	private List<Item> items1;
	private List<Item> items2;
	private List<Command> commands1;
	private List<Command> commands2;
	private Player player1;
	private Player player2;
	private Creature creature1;
	private Creature creature2;
	private Item item1;
	private Item item2;
	private Command command1;
	private Command command2;
	private Map map1;
	private Map map2;
	private Game game;
	private GameEngine gameEngine;
	
	@Before
	public void setUp() {		
		player1 = new Player();
		player1.setScore(100);
		
		player2 = new Player();
		player2.setScore(100000);
		
		characters1 = new ArrayList<Player>();
		characters1.add(player1);
		
		characters2 = new ArrayList<Player>();
		characters2.add(player1);
		characters2.add(player2);
		
		creature1 = new Creature();
		creature2 = new Creature();
		
		creatures1 = new ArrayList<Creature>();
		creatures1.add(creature1);
		
		creatures2 = new ArrayList<Creature>();
		creatures2.add(creature1);
		creatures2.add(creature2);
		
		item1 = new Item("Stick", "Small Branch", 0, 1, 0, 5, 10, 0, 0);
		item2 = new Item("Big Stick", "Bigger Branch", 0, 2, 2, 10, 20, 0, 0);
		
		items1 = new ArrayList<Item>();
		items1.add(item1);
		
		items2 = new ArrayList<Item>();
		items2.add(item1);
		items2.add(item2);
		
		command1 = new Command();
		command2 = new Command();
		
		commands1 = new ArrayList<Command>();
		commands1.add(command1);
		
		commands2 = new ArrayList<Command>();
		commands2.add(command1);
		commands2.add(command2);
		
		gameEngine = new GameEngine();
		
		map1 = new Map();
		map1.buildDefault(gameEngine);
		
		map2 = new Map();
				
		game = new Game(map1, player1, creatures1, items1, commands1);
	}
	
	@Test
	public void testGetMap() {
		assertEquals(map1, game.getMap());
	}
	
	@Test
	public void testSetMap() {
		game.setMap(map2);
		assertEquals(map2, game.getMap());
	}
	
	@Test
	public void testGetPlayer() {
		assertEquals(player1, game.getPlayer());
	}
	
	@Test
	public void testSetPlayer() {
		game.setPlayer(player2);
		assertEquals(player2, game.getPlayer());
	}
	
	@Test
	public void testGetCreatures() {
		assertEquals(creatures1, game.getCreatures());
	}
	
	@Test
	public void testSetCreatures() {
		game.setCreatures(creatures2);
		assertEquals(creatures2, game.getCreatures());
	}
	
	@Test
	public void testGetItems() {
		assertEquals(items1, game.getItems());
	}
	
	@Test
	public void testSetItems() {
		game.setItems(items2);
		assertEquals(items2, game.getItems());
	}
	
	@Test
	public void testGetCommands() {
		assertEquals(commands1, game.getCommands());
	}
	
	@Test
	public void testSetCommands() {
		game.setCommands(commands2);
		assertEquals(commands2, game.getCommands());
	}
}
