package game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import ycp.edu.cs320.adventure.game.Account;
import ycp.edu.cs320.adventure.game.Creature;
import ycp.edu.cs320.adventure.game.Game;
import ycp.edu.cs320.adventure.game.GameEngine;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Player;

public class AccountTest {
	private Account account;
	private List<Player> characters1;
	private List<Player> characters2;
	private List<Creature> creatures1;
	private List<Creature> creatures2;
	private List<Item> items1;
	private List<Item> items2;
	private List<Game> games1;
	private List<Game> games2;
	private Player player1;
	private Player player2;
	private Creature creature1;
	private Creature creature2;
	private Item item1;
	private Item item2;
	private Map map;
	private Game game1;
	private Game game2;
	private GameEngine gameEngine;
	
	@Before
	public void setUp() {
		account = new Account("username", "password");
		account.setId(0);
		
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
		
		gameEngine = new GameEngine();
		
		map = new Map();
		map.buildDefault();
				
		game1 = new Game(1, map, player1, creatures1, items1, null);
		game2 = new Game(2, map, player2, creatures2, items2, null);
		
		games1 = new ArrayList<Game>();
		games1.add(game1);
		
		games2 = new ArrayList<Game>();
		games2.add(game1);
		games2.add(game2);
		
		account.setCharacters(characters1);
		account.setGames(games1);
	}
	
	@Test
	public void testGetId() {
		assertEquals(0, account.getId());
	}
	
	@Test
	public void testSetId() {
		account.setId(1);
		assertEquals(1, account.getId());
	}
	
	@Test
	public void testGetPassword() {
		assertEquals("password", account.getPassword());
	}
	
	@Test
	public void testSetPassword() {
		account.setPassword("pass");
		assertEquals("pass", account.getPassword());
	}
	
	@Test
	public void testGetUsername() {
		assertEquals("username", account.getUsername());
	}
	
	@Test
	public void testSetUsername() {
		account.setUsername("user");
		assertEquals("user", account.getUsername());
	}
	
	@Test
	public void testGetCharacters() {
		assertEquals(characters1, account.getCharacters());
	}
	
	@Test
	public void testSetCharacters() {
		account.setCharacters(characters2);
		assertEquals(characters2, account.getCharacters());
	}
	
	@Test
	public void testGetGames() {
		assertEquals(games1, account.getGames());
	}
	
	@Test
	public void testSetGames() {
		account.setGames(games2);
		assertEquals(games2, account.getGames());
	}
}
