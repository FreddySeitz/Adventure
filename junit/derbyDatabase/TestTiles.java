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
				database.createTile(game_id, tiles[i][j].getType(), "Tile!", 1, tiles[i][j].getX(), tiles[i][j].getY());
			}
		}
		
		//update tiles, test get tile
		tiles[0][0] = database.getTile(game_id, 0, 0);
		database.updateTileDamage(9000, tiles[0][0].getTileId());
		database.updateTileType(1, tiles[0][0].getTileId());
		database.updateTileDescription("A different tile!", tiles[0][0].getTileId());
		tiles[0][0] = database.getTile(game_id, 0, 0);
		assertEquals(9000, tiles[0][0].getDamage());
		assertEquals(1, tiles[0][0].getType());
		assertEquals("A different tile!", tiles[0][0].getDescription());

		//update x and y, and test exists
		boolean exists = database.tileExists(game_id, 0, 0);
		assertEquals(true, exists);
		
		database.updateTileY(game_id, tiles[0][0].getTileId());
		database.updateTileX(game_id, tiles[0][0].getTileId());
		
		boolean exists2 = database.tileExists(game_id, 0, 0);
		assertEquals(false, exists2);
		
		//update all
		database.updateTileAll(2, "Trap!", 0, 0, tiles[0][0].getTileId());
		
		boolean exists3 = database.tileExists(game_id, 0, 0);
		assertEquals(true, exists3);
		
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
