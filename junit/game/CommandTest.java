package game;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import ycp.edu.cs320.adventure.game.Command;

public class CommandTest {
	private Command command;
	
	@Before
	public void setUp() {
		command = new Command();
		command.setCommand("Move up");
		command.setDescription("Moves the player up on the map");
	}
	
	@Test
	public void testGetCommand() {
		assertEquals("Move up", command.getCommand());
	}
	
	@Test
	public void testSetCommand() {
		command.setCommand("Move down");
		assertEquals("Move down", command.getCommand());
	}
	
	@Test
	public void testGetDescription() {
		assertEquals("Moves the player up on the map", command.getDescription());
	}
	
	@Test
	public void testSetDescription() {
		command.setDescription("Moves the player down on the map");
		assertEquals("Moves the player down on the map", command.getDescription());
	}
}
