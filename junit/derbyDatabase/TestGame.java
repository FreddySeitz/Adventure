package derbyDatabase;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

public class TestGame {
	DerbyDatabase database;
	int account_id;

	@Before
	public void setUp() {
		database = new DerbyDatabase();
		account_id = -999;
	}

	@Test
	//eclipse likes to run these tests in random order, so they have to be grouped into single tests
	public void testEverything() {
		//create two games
		int create = database.createGame(account_id);
		int create2 = database.createGame(account_id);
		assertTrue(create == create2 - 1);

		//gets games, should be 2
		List<Integer> games = database.getGames(account_id);
		int size = games.size();
		int id = games.get(0);
		int id2 = games.get(1);
		//game_id's should always be 1 apart
		assertEquals(2, size);
		assertEquals(id, id2-1);

		//remove all games of this account_id
		List<Integer> games2 = database.getGames(account_id);
		for(int i = 0; i < games2.size(); i++){
			boolean remove = database.removeGame(games2.get(i));
			assertEquals(true, remove);
		}

		//ensure there are no games to this account_id
		List<Integer> games3 = database.getGames(account_id);
		int size2 = games3.size();
		assertTrue(size2 == 0);
	}
}
