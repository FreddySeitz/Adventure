package game;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import ycp.edu.cs320.adventure.game.Player;

public class PlayerTest {
	private Player player;
	
	@Before
	public void setUp() {
		player = new Player();
		player.setScore(10);
	}
	
	@Test
	public void testGetScore() {
		assertEquals(10, player.getScore());
	}
	
	@Test
	public void testSetScore() {
		player.setScore(100);
		assertEquals(100, player.getScore());
	}
}
