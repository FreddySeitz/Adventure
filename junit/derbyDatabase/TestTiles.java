package derbyDatabase;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Tile;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

public class TestTiles {
	DerbyDatabase database;
	int game_id;
	int width;
	int height;
	Tile[][] tiles;


	@Before
	public void setUp() {
		database = new DerbyDatabase();
		game_id = -999;
		width = height = 20;
		tiles = new Tile[20][20];
	}

	@Test
	public void testEverything() {
		//creating tiles
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				tiles[i][j] = new Tile();
				tiles[i][j].setType(0);
				tiles[i][j].setX(j);
				tiles[i][j].setY(i);
				database.createTile(game_id, tiles[i][j].getType(), true, false, false, false, 0, "Tile!", 1, tiles[i][j].getX(), tiles[i][j].getY());
			}
		}

		//update tile, test get tile
		tiles[0][0] = database.getTile(game_id, 0, 0);
		database.updateTileDamage(9000, tiles[0][0].getTileId());
		database.updateTileType(1, tiles[0][0].getTileId());
		database.updateTileDescription("A different tile!", tiles[0][0].getTileId());
		database.updateTileVisible(false, tiles[0][0].getTileId());
		database.updateTileHidden(true, tiles[0][0].getTileId());
		database.updateTileActive(true, tiles[0][0].getTileId());
		database.updateTilePrompt(true, tiles[0][0].getTileId());
		database.updateTileQuestion(1, tiles[0][0].getTileId());
		tiles[0][0] = database.getTile(game_id, 0, 0);
		assertEquals(9000, tiles[0][0].getDamage());
		assertEquals(1, tiles[0][0].getType());
		assertEquals("A different tile!", tiles[0][0].getDescription());
		assertEquals(false, tiles[0][0].getVisible());
		assertEquals(true, tiles[0][0].getHidden());
		assertEquals(true, tiles[0][0].getActive());
		assertEquals(true, tiles[0][0].getPrompt());
		assertEquals(1, tiles[0][0].getQuestion());
		
		//update x and y, and test exists
		boolean exists = database.tileExists(game_id, 0, 0);
		assertEquals(true, exists);

		database.updateTileY(1, tiles[0][0].getTileId());
		database.updateTileX(1, tiles[0][0].getTileId());

		boolean exists2 = database.tileExists(game_id, 0, 0);
		assertEquals(false, exists2);

		//update all
		database.updateTileAll(2, true, false, false, false, 0, "Trap!", 0, 0, tiles[0][0].getTileId());

		boolean exists3 = database.tileExists(game_id, 0, 0);
		assertEquals(true, exists3);

		//testing update all
		int type = database.getTile(game_id, tiles[0][0].getX(), tiles[0][0].getY()).getType();
		boolean visible = database.getTile(game_id, tiles[0][0].getX(), tiles[0][0].getY()).getVisible();
		assertTrue(type == 2);
		assertEquals(true, visible);
		assertEquals(false, database.getTile(game_id, tiles[0][0].getX(), tiles[0][0].getY()).getHidden());
		assertEquals(false, database.getTile(game_id, tiles[0][0].getX(), tiles[0][0].getY()).getActive());
		assertEquals(false, database.getTile(game_id, tiles[0][0].getX(), tiles[0][0].getY()).getPrompt());
		assertEquals(0, database.getTile(game_id, tiles[0][0].getX(), tiles[0][0].getY()).getQuestion());

		//tests get all tiles
		List<Tile> list = database.getAllTiles(game_id);
		int size = list.size();
		assertEquals(400,size);

		//deletes all tiles
		for(int i = 0; i < list.size(); i++){
			database.removeTile(list.get(i).getTileId());
		}
	}
}
