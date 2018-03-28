package game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import ycp.edu.cs320.adventure.game.Creature;

public class CreatureTest {
	private Creature creature;
	
	@Before
	public void setUp() {
		creature = new Creature();
		creature.setMovementSpeed(1);
	}
	
	@Test
	public void testGetMovementSpeed() {
		assertEquals(1, creature.getMovementSpeed());
	}
	
	@Test
	public void testSetMovementSpeed() {
		creature.setMovementSpeed(2);
		assertEquals(2, creature.getMovementSpeed());
	}
}
