package game;

import static org.junit.Assert.assertTrue;

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
}
